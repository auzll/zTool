package z.tool.codegen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.io.Resources;

import com.google.common.collect.Sets;

public class Demo {
    public static void main(String[] args) throws Exception {
        Properties properties = Resources.getResourceAsProperties("c3p0.properties");
        Class.forName(properties.getProperty("JDBC.driver"));
        
        Connection conn = DriverManager.getConnection(
                properties.getProperty("JDBC.url"), 
                properties.getProperty("JDBC.username"), 
                properties.getProperty("JDBC.password"));
        
        Set<String> enumColumns = Sets.newHashSet();
        enumColumns.add("resource_status");
        
        ClassGenerator generator = new ClassGenerator(
                "/home/workspace/zTool/src/java", 
                "test.z.tool", 
                conn,
                enumColumns);
        
        generator.generate();
    }
}
