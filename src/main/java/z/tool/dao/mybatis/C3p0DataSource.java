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
