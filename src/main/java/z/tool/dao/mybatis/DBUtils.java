/**
 * https://github.com/auzll/zTool
 */
package z.tool.dao.mybatis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;

public final class DBUtils {
    private static final Log LOG = LogFactory.getLog(DBUtils.class);
    
    public static class DataSourceHolder {
        public static final DataSource DATA_SOURCE = newDataSource();
        
        private static DataSource newDataSource() {
            try {
                C3p0DataSource c3p0DataSource = new C3p0DataSource();
                c3p0DataSource.setProperties(Resources.getResourceAsProperties("c3p0.properties"));
                return c3p0DataSource.getDataSource();
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }
    
    public static void closeQuietly(Connection target1, Statement target2, ResultSet target3) {
        closeQuietly(target1);
        closeQuietly(target2);
        closeQuietly(target3);
    }
    
    public static void closeQuietly(Connection target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("method:closeQuietly,errorMsg:" + e.getMessage(), e);
                }
            }
        }
    }
    
    public static void closeQuietly(Statement target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("method:closeQuietly,errorMsg:" + e.getMessage(), e);
                }
            }
        }
    }
    
    public static void closeQuietly(ResultSet target) {
        if (null != target) {
            try {
                target.close();
            } catch (SQLException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("method:closeQuietly,errorMsg:" + e.getMessage(), e);
                }
            }
        }
    }
}
