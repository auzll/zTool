package z.tool.codegen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import z.tool.util.SimpleLogUtil;

final class GenUtil {
    private GenUtil() {}
    
    public static final String CHARSET = "utf-8";
    
    private static final String LICENSE_FILE = "LICENSE";
    private static final String MYBATIS_CONFIG_TPL_FILE = "z/tool/codegen/mybatis-config.tpl";
    private static final String MYBATIS_DAO_FILE = "z/tool/codegen/mybatis-dao.tpl";
    private static final String ABSTRACT_SERVICE_FILE = "z/tool/codegen/service-abstract.tpl";
    private static final String SERVICE_IMPL_FILE = "z/tool/codegen/service-impl.tpl";
    
    private static final String REST_ITEM_FILE = "z/tool/codegen/resteasy-item.tpl";
    private static final String REST_ITEM_LIST_FILE = "z/tool/codegen/resteasy-item-list.tpl";
    private static final String REST_ITEM_PAGE_FILE = "z/tool/codegen/resteasy-item-page.tpl";
    private static final String REST_RESOURCE_FILE = "z/tool/codegen/resteasy-resource.tpl";
    
    public static final String LICENSE;
    public static final String MYBATIS_CONFIG_TPL;
    public static final String MYBATIS_DAO;
    public static final String ABSTRACT_SERVICE;
    public static final String SERVICE_IMPL;
    
    public static final String REST_ITEM;
    public static final String REST_ITEM_LIST;
    public static final String REST_ITEM_PAGE;
    public static final String REST_RESOURCE;
    
    public static final String ABOUT = new StringBuilder().append("/** \r\n")
            .append("* TODO 以下代码基于模板生成，可能还需根据实际情况做调整 \r\n")
            .append("* \r\n")
            .append("* @author auzll \r\n")
            .append("*/").toString();
    
    static {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(LICENSE_FILE), CHARSET));
            String line = null;
            StringBuilder buff = new StringBuilder();
            
            buff.append("/**\r\n");
            while (null != (line = reader.readLine())) {
                buff.append("* ").append(line).append("\r\n");
            }
            buff.append("*/");
            LICENSE = buff.toString();
            
            MYBATIS_CONFIG_TPL = readFile(MYBATIS_CONFIG_TPL_FILE);
            MYBATIS_DAO = readFile(MYBATIS_DAO_FILE);
            ABSTRACT_SERVICE = readFile(ABSTRACT_SERVICE_FILE);
            SERVICE_IMPL = readFile(SERVICE_IMPL_FILE);
            
            REST_ITEM = readFile(REST_ITEM_FILE);
            REST_ITEM_LIST = readFile(REST_ITEM_LIST_FILE);
            REST_ITEM_PAGE = readFile(REST_ITEM_PAGE_FILE);
            REST_RESOURCE = readFile(REST_RESOURCE_FILE);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    static String readFile(String file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(file), CHARSET));
            String line = null;
            StringBuilder buff = new StringBuilder();
            
            while (null != (line = reader.readLine())) {
                buff.append(line).append("\r\n");
            }
            return buff.toString();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static Class<?> getClazz(JdbcType type) {
        switch (type) {
            case VARCHAR: case LONGVARCHAR:
                return String.class;
            case TIMESTAMP:
                return Date.class;
            case INTEGER:
                return int.class;
            case BIGINT:
                return int.class;
            default:
                throw new IllegalArgumentException("unknow type[" + type + "]");
        }
    }
    
    public static GenField getField(String columnName, JdbcType type) {
        Class<?> clazz = getClazz(type);
        if (int.class == clazz) {
            if (columnName.endsWith("id")
                    || columnName.endsWith("creator")
                    || columnName.endsWith("updator")) {
                clazz = long.class;
            }
            
            if (columnName.startsWith("parent")) {
                return new GenField(columnName, "parent");
            }
        }
        return new GenField(clazz, columnName);
    }
    
    public static String tableNameToClassName(String tableName) {
        tableName = tableName.toLowerCase();
        if (tableName.startsWith("tb")) {
            tableName = tableName.substring(2);
        }
        while (tableName.startsWith("_")) {
            tableName = tableName.substring(1);
        }
        char[] names = tableName.toCharArray();
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (0 == i) {
                buff.append(Character.toUpperCase(names[i]));
            } else {
                if ('_' == names[i]) {
                    i++;
                    while (i < names.length && '_' == names[i]) {
                        i++;
                    }
                    buff.append(Character.toUpperCase(names[i]));
                } else {
                    buff.append(names[i]);
                }
            }
        }
        
        return buff.toString();
    }
    
    public static String columnNameToFieldName(String fieldName) {
        fieldName = fieldName.toLowerCase();
        while (fieldName.startsWith("_")) {
            fieldName = fieldName.substring(1);
        }
        char[] names = fieldName.toCharArray();
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if ('_' == names[i]) {
                i++;
                while (i < names.length && '_' == names[i]) {
                    i++;
                }
                buff.append(Character.toUpperCase(names[i]));
            } else {
                buff.append(names[i]);
            }
        }
        
        return buff.toString();
    }
    
    public static boolean ignoreImport(Class<?> clazz) {
        if (int.class == clazz
                || short.class == clazz
                || long.class == clazz
                || float.class == clazz
                || double.class == clazz
                || byte.class == clazz
                || char.class == clazz
                || boolean.class == clazz) {
            return true;
        }
        
        if (clazz.getName().startsWith("java.lang.")) {
            return true;
        }
        
        return false;
    }
    
    public static void writeFile(File classFile, String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile), GenUtil.CHARSET));
            writer.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void log(String data) {
        SimpleLogUtil.info(data);
    }
    
    public static File mkdir(File parent, String newDirName) {
        if (!parent.exists()) {
            parent.mkdirs();
        }
        File f = new File(parent, newDirName);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }
}
