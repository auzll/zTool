/**
 * https://github.com/auzll/zTool
 */
package z.tool.dao.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3p0DataSource
 */
public class C3p0DataSource implements DataSourceFactory {
    
    private final ComboPooledDataSource dataSource = new ComboPooledDataSource();

    public void setProperties(Properties props) {
        try {
            dataSource.setDriverClass(props.getProperty("JDBC.driver"));
            dataSource.setJdbcUrl(props.getProperty("JDBC.url"));
            dataSource.setUser(props.getProperty("JDBC.username"));
            dataSource.setPassword(props.getProperty("JDBC.password"));
            dataSource.setAutoCommitOnClose(Boolean.valueOf(props.getProperty("JDBC.autoCommit")));
            dataSource.setAcquireIncrement(Integer.valueOf(props.getProperty("c3p0.acquireIncrement")));
            dataSource.setCheckoutTimeout(Integer.valueOf(props.getProperty("c3p0.checkoutTimeout")));
            dataSource.setInitialPoolSize(Integer.valueOf(props.getProperty("c3p0.initialPoolSize")));
            dataSource.setMaxIdleTime(Integer.valueOf(props.getProperty("c3p0.maxIdleTime")));
            dataSource.setMaxIdleTimeExcessConnections(Integer.valueOf(props.getProperty("c3p0.maxIdleTimeExcessConnections")));
            dataSource.setMaxPoolSize(Integer.valueOf(props.getProperty("c3p0.maxPoolSize")));
            dataSource.setMinPoolSize(Integer.valueOf(props.getProperty("c3p0.minPoolSize")));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
