/**
 * https://github.com/auzll/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package z.tool.dao.mybatis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.log4j.Logger;

public final class DBUtils {
    private static final Logger LOG = Logger.getLogger(DBUtils.class);
    
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
