package z.tool.codegen;

import java.util.Set;

import z.tool.util.ToStringBuilder;

class GenField {
    private final Class<?> type;
    private final String columnName;
    private final String name;
    
    public GenField(Class<?> type, String columnName) {
        this.type = type;
        this.columnName = columnName;
        this.name = GenUtil.columnNameToFieldName(columnName);
    }
    
//    public GenField(String columnName) {
//        this.type = null;
//        this.columnName = columnName;
//        this.name = GenUtil.columnNameToFieldName(columnName);
//    }
    
    public GenField(String columnName, String name) {
        this.type = null;
        this.columnName = columnName;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder()
            .add("type", null != type ? type.getName() : null)
            .add("columnName", columnName)
            .add("name", name)
            .build();
    }

    public Class<?> getType() {
        return type;
    }
    
    public String getDefineType(GenClass clazz) {
        return null != type ? type.getSimpleName() : clazz.getName();
    }

    public String getColumnName() {
        return columnName;
    }

    public String getName() {
        return name;
    }
    
    public String getMybatisPropertyName(Set<String> enumColumns) {
        if (enumColumns.contains(columnName)) {
            return name + ".enName";
        }
        if ("creator".equals(columnName) || "updator".equals(columnName)) {
            return name + ".id";
        }
        return name;
    }
}
