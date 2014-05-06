package z.tool.codegen;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

public class ClassGenerator {
    private String srcPath;
    private String packageName;
    private Connection conn;
    private Set<String> enumColumns    ;

    public ClassGenerator(String srcPath, String packageName, Connection conn, Set<String> enumColumns    ) {
        this.srcPath = srcPath;
        this.packageName = packageName;
        this.conn = conn;
        this.enumColumns = null != enumColumns ? enumColumns : new HashSet<String>();
    }
    
    public void generate() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("show tables");
            List<String> tables = Lists.newArrayList();
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
            rs.close();
            
            String packageFilePath = srcPath + File.separator + packageName.replace(".", File.separator);
            File dir = new File(packageFilePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            boolean createAbstractService = false;
            
            for (String table : tables) {
                GenClass genClass = new GenClass(table);
                
                rs = stmt.executeQuery("select * from " + table + " limit 1");
                ResultSetMetaData meta = rs.getMetaData();
                for (int i = 1, max = meta.getColumnCount(); i <= max; i++) {
                    genClass.addField(
                        GenUtil.getField(meta.getColumnName(i) , 
                        JdbcType.forCode(meta.getColumnType(i)))
                    );
                }
                
                // entity
                GenUtil.writeFile(
                    new File(GenUtil.mkdir(dir, "entity"), genClass.getName() + ".java"), 
                    genClass.toEntityClass(packageName + ".entity")
                );
                
                // dao
                File daoDir = GenUtil.mkdir(dir, "dao");
                GenUtil.writeFile(
                    new File(daoDir, genClass.getName() + "Dao.java"), 
                    genClass.toDaoClass(packageName + ".entity", packageName + ".dao")
                );
                
                // mybatis
                File mybatisDir = GenUtil.mkdir(daoDir, "mybatis");
                GenUtil.writeFile(
                    new File(mybatisDir, genClass.getName() + "Dao.xml"), 
                    genClass.toDaoXmlFile(enumColumns, packageName + ".entity", packageName + ".dao")
                );
                
                // service
                File serviceDir = GenUtil.mkdir(dir, "service");
                GenUtil.writeFile(
                    new File(serviceDir, genClass.getName() + "Service.java"), 
                    genClass.toServiceClass(packageName + ".entity", packageName + ".dao", packageName + ".service")
                );
                
                // service impl
                File serviceImplDir = GenUtil.mkdir(serviceDir, "impl");
                if (!createAbstractService) {
                    createAbstractService = true;
                    GenUtil.writeFile(
                        new File(serviceImplDir, "AbstractService.java"), 
                        genClass.toAbstractServiceClass(packageName + ".service.impl", 
                                GenUtil.ABSTRACT_SERVICE)
                    );
                }
                GenUtil.writeFile(
                    new File(serviceImplDir, genClass.getName() + "ServiceImpl.java"), 
                    genClass.toServiceImplClass(
                            packageName + ".entity", 
                            packageName + ".dao", 
                            packageName + ".service", 
                            packageName + ".service.impl")
                );
                
                // web
                File webDir = GenUtil.mkdir(dir, "web");
                
                // rest
                File restDir = GenUtil.mkdir(webDir, "rest");
                GenUtil.writeFile(
                    new File(restDir, genClass.getName() + "Resource.java"), 
                    genClass.toRestResource(packageName + ".web.rest", 
                            packageName + ".entity", genClass.getName())
                );
                
                File respDir = GenUtil.mkdir(restDir, "resp");
                GenUtil.writeFile(
                    new File(respDir, genClass.getName() + "Item.java"), 
                    genClass.toRestRespItem(packageName + ".web.rest.resp", 
                            packageName + ".entity", genClass.getName())
                );
                GenUtil.writeFile(
                    new File(respDir, genClass.getName() + "ItemList.java"), 
                    genClass.toRestRespItemList(packageName + ".web.rest.resp", 
                            packageName + ".entity", genClass.getName())
                );
                GenUtil.writeFile(
                    new File(respDir, genClass.getName() + "ItemPage.java"), 
                    genClass.toRestRespItemPage(packageName + ".web.rest.resp", 
                            packageName + ".entity", genClass.getName())
                );
                
                GenUtil.log("DONE -> " + genClass.getName());
                
            }
        } finally {
            stmt.close();
        }
    }
    
    
}
