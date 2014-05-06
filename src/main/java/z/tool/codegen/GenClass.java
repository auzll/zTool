package z.tool.codegen;

import java.util.List;
import java.util.Set;

import z.tool.util.StringUtil;
import z.tool.util.ToStringBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

final class GenClass {
    private final String tableName;
    private final String name;
    private List<GenField> fields;
    
    static String LINE = "\r\n";
    static String INDENT = "    ";
    
    public GenClass(String tableName) {
        this.tableName = tableName;
        this.name = GenUtil.tableNameToClassName(tableName);
    }

    public String getName() {
        return name;
    }

    public List<GenField> getFields() {
        return fields;
    }
    
    public GenClass addField(GenField field) {
        if (null == fields) {
            fields = Lists.newArrayList();
        }
        fields.add(field);
        return this;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("tableName", tableName)
            .add("name", name)
            .add("fields", fields)
            .build();
    }

    public String getTableName() {
        return tableName;
    }
    
    private void entityImportClass(StringBuilder buff) {
        if (null == fields || fields.size() < 1) {
            return;
        }
        
        buff.append(LINE);
        
        Set<String> dupl = Sets.newHashSet();
        for (GenField field : fields) {
            if (null == field.getType() || GenUtil.ignoreImport(field.getType())) {
                continue;
            }
            
            if (dupl.contains(field.getType().getName())) {
                continue;
            }
            dupl.add(field.getType().getName());
            
            buff.append(LINE).append("import ");
            buff.append(field.getType().getName());
            buff.append(";");
        }
        
        buff.append(LINE).append("import z.tool.util.ToStringBuilder;");
        buff.append(LINE).append("import z.tool.entity.json.*;");
    }
    
    private void entityDefineFields(StringBuilder buff) {
        if (null == fields || fields.size() < 1) {
            return;
        }
        
        buff.append(LINE).append(INDENT)
            .append("private static final long serialVersionUID = ").append(System.currentTimeMillis()).append("L;")
            .append(LINE);
        
        for (GenField field : fields) {
            buff.append(LINE)
                .append(INDENT)
                .append("private ")
                .append(field.getDefineType(this))
                .append(" ")
                .append(field.getName())
                .append(";");
        }
    }
    
    private void entityDefineMethods(StringBuilder buff) {
        if (null == fields || fields.size() < 1) {
            return;
        }
        
        buff.append(LINE).append(LINE).append(INDENT).append("public ").append(name).append("() {}")
            .append(LINE).append(LINE).append(INDENT).append("public ").append(name).append("(long id) { this.id = id; }")
            .append(LINE);
        
        for (GenField field : fields) {
            // getter
            buff.append(LINE)
                .append(INDENT)
                .append("public ")
                .append(field.getDefineType(this))
                .append(" get")
                .append(Character.toUpperCase(field.getName().charAt(0)))
                .append(field.getName().substring(1))
                .append("() { ")
                .append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append("return this.")
                .append(field.getName())
                .append("; ")
                .append(LINE)
                .append(INDENT)
                .append("}")
                .append(LINE);
            
            if (field.getName().endsWith("creator")
                    || field.getName().endsWith("updator")) {
                buff.append(LINE).append(INDENT).append("//").append(" TODO ");
                
                // getter, getCreatorId, getUpdatorId
                buff.append(LINE)
                    .append(INDENT)
                    .append("public long get")
                    .append(Character.toUpperCase(field.getName().charAt(0)))
                    .append(field.getName().substring(1))
                    .append("Id() { ")
                    .append(LINE)
                    .append(INDENT)
                    .append(INDENT)
                    .append("return this.")
                    .append(field.getName())
                    .append("; ")
                    .append(LINE)
                    .append(INDENT)
                    .append("}")
                    .append(LINE);
                
            } else if (field.getName().startsWith("parent")) {
                buff.append(LINE).append(INDENT).append("//").append(" TODO ");
                
                // getter, getParentId
                buff.append(LINE)
                    .append(INDENT)
                    .append("public long get")
                    .append(Character.toUpperCase(field.getName().charAt(0)))
                    .append(field.getName().substring(1))
                    .append("Id() { ")
                    .append(LINE)
                    .append(INDENT)
                    .append(INDENT)
                    .append("return this.")
                    .append(field.getName())
                    .append(".id; ")
                    .append(LINE)
                    .append(INDENT)
                    .append("}")
                    .append(LINE);
            }
            
            // setter
            buff.append(LINE)
                .append(INDENT)
                .append("public void set")
                .append(Character.toUpperCase(field.getName().charAt(0)))
                .append(field.getName().substring(1))
                .append("(")
                .append(field.getDefineType(this))
                .append(" ")
                .append(field.getName())
                .append(") { ")
                .append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append("this.")
                .append(field.getName())
                .append(" = ")
                .append(field.getName())
                .append("; ")
                .append(LINE)
                .append(INDENT)
                .append("}")
                .append(LINE);
            
            // link setter
            buff.append(LINE)
                .append(INDENT)
                .append("public ")
                .append(name)
                .append(" ")
                .append(field.getName())
                .append("(")
                .append(field.getDefineType(this))
                .append(" ")
                .append(field.getName())
                .append(") { ")
                .append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append("this.")
                .append(field.getName())
                .append(" = ")
                .append(field.getName())
                .append("; ")
                .append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append("return this;")
                .append(LINE)
                .append(INDENT)
                .append("}")
                .append(LINE);
        }
        
        buff.append(LINE)
            .append(INDENT)
            .append("protected ToStringBuilder toStringBuilder() { ")
            .append(LINE)
            .append(INDENT)
            .append(INDENT)
            .append("return new ToStringBuilder()");
        for (GenField field : fields) {
            if (field.getName().endsWith("id")) {
                buff.append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append(INDENT)
                .append(".addId(\"").append(field.getName()).append("\", ").append("this.id)");
            } else if (field.getName().endsWith("creator") 
                    || field.getName().endsWith("updator")
                    || field.getName().endsWith("detail")
                    || field.getName().endsWith("createTime")
                    || field.getName().endsWith("updateTime")
                    || field.getName().endsWith("parent")) {
                continue;
            } else {
                buff.append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append(INDENT)
                .append(".add(\"").append(field.getName()).append("\", ").append("this.").append(field.getName()).append(")");
            }
        }
        buff.append(LINE)
            .append(INDENT)
            .append(INDENT)
            .append(INDENT)
            .append(";")
            .append(LINE)
            .append(INDENT)
            .append("}");
        
        buff.append(LINE)
            .append(LINE)
            .append(INDENT)
            .append("@Override public String toString() { ")
            .append(LINE)
            .append(INDENT)
            .append(INDENT)
            .append("return this.toStringBuilder().build();")
            .append(LINE)
            .append(INDENT)
            .append("}");
        
        buff.append(LINE)
            .append(LINE)
            .append(INDENT)
            .append("@Override public ").append(name).append(" loadFromJson(JsonObject json) {");
        for (GenField field : fields) {
            buff.append(LINE)
                .append(INDENT)
                .append(INDENT)
                .append("if (json.containsKey(\"").append(field.getName()).append("\")) { ")
                .append("this.").append(field.getName()).append(" = ");
            String t = field.getDefineType(this);
            if (t.equals("boolean")) {
                buff.append("json.getBoolean(\"").append(field.getName()).append("\"); }");
            } else if (t.equals("long")) {
                buff.append("json.getLong(\"").append(field.getName()).append("\"); }");
            } else if (t.equals("int")) {
                buff.append("json.getIntValue(\"").append(field.getName()).append("\"); }");
            } else if (t.equals("String")) {
                buff.append("json.getString(\"").append(field.getName()).append("\"); }");
            } else if (t.equals("Date")) {
                buff.append("new Date(json.getLong(\"").append(field.getName()).append("\")); }");
            } else {
                throw new RuntimeException("unknow type[" + t + "]");
            }
        }
        buff.append(LINE)
            .append(INDENT)
            .append(INDENT)
            .append("return this;")
            .append(LINE)
            .append(INDENT)
            .append("}");
        
        buff.append(LINE)
            .append(LINE)
            .append(INDENT)
            .append("@Override public JsonObject toJson() {")
            .append(LINE)
            .append(INDENT)
            .append(INDENT)
            .append("return Jsons.newJsonObject()");
        for (GenField field : fields) {
            buff.append(LINE)
                .append(INDENT)
                .append(INDENT);
            if (field.getColumnName().endsWith("id")) {
                buff.append(".putId(\"").append(field.getName()).append("\", ").append(field.getName()).append(")");
            } else {
                buff.append(".put(\"").append(field.getName()).append("\", ").append(field.getName()).append(")");
            }
        }
        buff.append(LINE)
            .append(INDENT)
            .append(INDENT)
            .append(";")
            .append(LINE)
            .append(INDENT)
            .append("}");
        
        /*
         * @Override public JsonObject toJson() {
        return Jsons.newJsonObject()
                .putId("websiteId", websiteId)
                .put("domain", domain);
    }
         */
    }
    
    public String toEntityClass(String packageName) {
        StringBuilder buff = new StringBuilder();
        buff.append(GenUtil.LICENSE);
        buff.append(LINE).append("package ").append(packageName).append(";");
        
        entityImportClass(buff);
        
        buff.append(LINE).append(LINE);
        
        // 类注释
        buff.append(GenUtil.ABOUT);
        
        buff.append(LINE).append("public final class ").append(name).append(" implements java.io.Serializable, Jsonable<").append(name).append("> {");
        entityDefineFields(buff);
        entityDefineMethods(buff);
        buff.append(LINE).append("}");
        
        return buff.toString();
    }
    
    public String toRestRespItem(String restEntityPackageName, String entityPackageName, String entityName) {
        return GenUtil.REST_ITEM.replace("${LICENSE}", GenUtil.LICENSE)
                    .replace("${ABOUT}", GenUtil.ABOUT)
                    .replace("${restEntityPackageName}", restEntityPackageName)
                    .replace("${entityPackageName}", entityPackageName)
                    .replace("${entityName}", entityName);
    }
    
    public String toRestRespItemList(String restEntityPackageName, String entityPackageName, String entityName) {
        return GenUtil.REST_ITEM_LIST.replace("${LICENSE}", GenUtil.LICENSE)
                    .replace("${ABOUT}", GenUtil.ABOUT)
                    .replace("${restEntityPackageName}", restEntityPackageName)
                    .replace("${entityPackageName}", entityPackageName)
                    .replace("${entityName}", entityName);
    }
    
    public String toRestRespItemPage(String restEntityPackageName, String entityPackageName, String entityName) {
        return GenUtil.REST_ITEM_PAGE.replace("${LICENSE}", GenUtil.LICENSE)
                    .replace("${ABOUT}", GenUtil.ABOUT)
                    .replace("${restEntityPackageName}", restEntityPackageName)
                    .replace("${entityPackageName}", entityPackageName)
                    .replace("${entityName}", entityName);
    }
    
    public String toRestResource(String restPackageName, String entityPackageName, String entityName) {
        return GenUtil.REST_RESOURCE.replace("${LICENSE}", GenUtil.LICENSE)
                    .replace("${ABOUT}", GenUtil.ABOUT)
                    .replace("${restPackageName}", restPackageName)
                    .replace("${entityPackageName}", entityPackageName)
                    .replace("${entityName}", entityName)
                    .replace("${lowerEntityName}", Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1));
    }
    
    public String toServiceClass(String entityPackage, String daoPackageName, String servicePackageName) {
        StringBuilder buff = new StringBuilder();
        buff.append(GenUtil.LICENSE);
        buff.append(LINE).append("package ").append(servicePackageName).append(";");
        
        buff.append(LINE).append(LINE).append("import java.util.List;");
        buff.append(LINE).append("import z.tool.dao.QueryCondition;");
        buff.append(LINE).append("import z.tool.dao.QueryResult;");
        buff.append(LINE).append("import ").append(entityPackage).append(".").append(name).append(";");
        
        buff.append(LINE).append(LINE);
        
        // 类注释
        buff.append(GenUtil.ABOUT);
        
        buff.append(LINE).append("public interface ").append(name).append("Service {");
        buff.append(LINE).append(INDENT).append(name).append(" save(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append(name).append(" delete(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append(name).append(" update(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append(name).append(" querySimple(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append(name).append(" queryDetail(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append("QueryResult<").append(name).append("> queryPage(QueryCondition<").append(name).append("> condition);");
        buff.append(LINE).append(INDENT).append("List<").append(name).append("> queryListByIdList(List<Long> idList);");
        
        buff.append(LINE).append("}");
        
        return buff.toString();
    }
    
    public String toAbstractServiceClass(String serviceImplPackageName, String data) {
        StringBuilder buff = new StringBuilder();
        buff.append(GenUtil.LICENSE);
        buff.append(LINE).append("package ").append(serviceImplPackageName).append(";");
        
        buff.append(LINE).append(LINE);
        
        // 类注释
        buff.append(GenUtil.ABOUT);
        
        buff.append(LINE).append(data);
        
        return buff.toString();
    }
    
    public String toServiceImplClass(String entityPackage, String daoPackageName, String servicePackageName, String serviceImplPackageName) {
        StringBuilder buff = new StringBuilder();
        buff.append(GenUtil.LICENSE);
        buff.append(LINE).append("package ").append(serviceImplPackageName).append(";");
        
        buff.append(LINE).append(LINE).append("import z.tool.util.*;");
        buff.append(LINE).append("import org.apache.log4j.Logger;");
        buff.append(LINE).append("import com.google.inject.*;");
        buff.append(LINE).append("import org.mybatis.guice.transactional.Transactional;");
        buff.append(LINE).append("import z.tool.dao.*;");
        buff.append(LINE).append("import java.util.*;");
        buff.append(LINE).append("import z.tool.checker.*;");
        buff.append(LINE).append("import ").append(entityPackage).append(".").append(name).append(";");
        buff.append(LINE).append("import ").append(daoPackageName).append(".").append(name).append("Dao;");
        buff.append(LINE).append("import ").append(servicePackageName).append(".").append(name).append("Service;");
        
        buff.append(LINE).append(LINE);
        
        // 类注释
        buff.append(GenUtil.ABOUT);
        
        buff.append(LINE).append(GenUtil.SERVICE_IMPL
            .replace("${entityName}", name)
            .replace("${lowerEntityName}", Character.toLowerCase(name.charAt(0)) + name.substring(1))
        );
        
        return buff.toString();
    }
    
    public String toDaoClass(String entityPackage, String daoPackageName) {
        StringBuilder buff = new StringBuilder();
        buff.append(GenUtil.LICENSE);
        buff.append(LINE).append("package ").append(daoPackageName).append(";");
        
        buff.append(LINE).append(LINE).append("import java.util.List;");
        buff.append(LINE).append("import z.tool.dao.QueryCondition;");
        buff.append(LINE).append("import ").append(entityPackage).append(".").append(name).append(";");
        
        buff.append(LINE).append(LINE);
        
        // 类注释
        buff.append(GenUtil.ABOUT);
        
        buff.append(LINE).append("public interface ").append(name).append("Dao {");
        buff.append(LINE).append(INDENT).append("int save(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append("int delete(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append("int update(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append(name).append(" queryById(").append(name).append(" ").append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).append(");");
        buff.append(LINE).append(INDENT).append("List<").append(name).append("> queryPageList(QueryCondition<").append(name).append("> condition);");
        buff.append(LINE).append(INDENT).append("List<").append(name).append("> queryListByIdList(List<Long> idList);");
        buff.append(LINE).append(INDENT).append("int queryPageCount(QueryCondition<").append(name).append("> condition);");
        
        buff.append(LINE).append("}");
        
        return buff.toString();
    }
    
    public String toDaoXmlFile(Set<String> enumColumns, String entityPackage, String daoPackageName) {
        StringBuilder buff = new StringBuilder();
        buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buff.append(LINE).append("<!--");
        buff.append(LINE).append(GenUtil.LICENSE);
        buff.append(LINE).append("-->").append(LINE);
        
        buff.append(LINE).append("<!--");
        buff.append(LINE).append(GenUtil.ABOUT);
        buff.append(LINE).append("-->").append(LINE);
        
        // <!--resultMap-->
        StringBuilder resultMap = new StringBuilder();
        boolean flag = false;
        for (GenField field : fields) {
            if (!flag) {
                flag = true;
            } else {
                resultMap.append(INDENT).append(INDENT);
            }
            resultMap.append("<result property=\"")
                .append(field.getName())
                .append("\" column=\"")
                .append(field.getColumnName())
                .append("\" />")
                .append(LINE)
                ;
        }
        StringUtil.deleteEnd(resultMap, LINE);
        
        // <!--save-->
        StringBuilder save = new StringBuilder();
        save.append("insert into ")
            .append(tableName)
            .append("(")
            ;
        for (int i = 0, max = fields.size(); i < max; ) {
            for (int j = 0; j < 3 && i < max; j++, i++) {
                GenField field = fields.get(i);
                if (field.getName().equals("id")) {
                    continue;
                }
                save.append(field.getColumnName()).append(", ");
            }
            if (i < max-1) {
                save.append(LINE).append(INDENT).append(INDENT);
            }
        }
        StringUtil.deleteEnd(save, ", ");
        save.append(") ");
        save.append(LINE).append(INDENT).append(INDENT).append("values (");
        for (int i = 0, max = fields.size(); i < max; ) {
            for (int j = 0; j < 3 && i < max; j++, i++) {
                GenField field = fields.get(i);
                if (field.getName().equals("id")) {
                    continue;
                }
                save.append("#{").append(field.getMybatisPropertyName(enumColumns)).append("}, ");
            }
            if (i < max-1) {
                save.append(LINE).append(INDENT).append(INDENT);
            }
        }
        StringUtil.deleteEnd(save, ", ");
        save.append(")");
        
        // <!--update-->
        StringBuilder update = new StringBuilder();
        update.append("update ")
            .append(tableName)
            .append(LINE).append(INDENT).append(INDENT)
            .append("set ")
            ;
        for (int i = 0, max = fields.size(); i < max; i++) {
            GenField field = fields.get(i);
            if (field.getColumnName().equals("id")
                    || field.getColumnName().equals("creator")
                    || field.getColumnName().equals("create_time")) {
                continue;
            }
            update.append(field.getColumnName()).append(" = ").append("#{").append(field.getMybatisPropertyName(enumColumns)).append("}, ");
            if (i < max-1) {
                update.append(LINE).append(INDENT).append(INDENT).append(INDENT);
            }
        }
        StringUtil.deleteEnd(update, ", ");
        update.append(LINE).append(INDENT).append(INDENT).append("where id = #{id} and creator = #{creator.id}");
        
        String daoTpl = GenUtil.MYBATIS_DAO
                .replace("${daoFullName}", daoPackageName + "." + name + "Dao")
                .replace("${entityName}", name)
                .replace("${entityPackageName}", entityPackage)
                .replace("${tableName}", tableName)
                .replace("<!--resultMap-->", resultMap.toString())
                .replace("<!--save-->", save.toString())
                .replace("<!--update-->", update.toString());
        
        buff.append(daoTpl);
        
        return buff.toString();
    }
}
