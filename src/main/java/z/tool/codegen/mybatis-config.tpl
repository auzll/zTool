<!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <properties resource="c3p0.properties" />

    <typeAliases>
        <typeAlias type="z.tool.dao.mybatis.C3p0DataSource" alias="C3P0"/>
    </typeAliases>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC" />
            <dataSource type="C3P0">
                <property name="JDBC.driver" value="${JDBC.driver}" />
                <property name="JDBC.url" value="${JDBC.url}" />
                <property name="JDBC.username" value="${JDBC.username}" />
                <property name="JDBC.password" value="${JDBC.password}" />
                <property name="JDBC.autoCommit" value="${JDBC.autoCommit}" />
                <property name="c3p0.acquireIncrement" value="${c3p0.acquireIncrement}" />
                <property name="c3p0.checkoutTimeout" value="${c3p0.checkoutTimeout}" />
                <property name="c3p0.initialPoolSize" value="${c3p0.initialPoolSize}" />
                <property name="c3p0.maxIdleTime" value="${c3p0.maxIdleTime}" />
                <property name="c3p0.maxIdleTimeExcessConnections" value="${c3p0.maxIdleTimeExcessConnections}" />
                <property name="c3p0.maxPoolSize" value="${c3p0.maxPoolSize}" />
                <property name="c3p0.minPoolSize" value="${c3p0.minPoolSize}" />
            </dataSource> 
        </environment>
    </environments>
    
    <mappers>
        <!-- <mapper resource="z/xxx/XXXDao.xml" /> -->
        <!--mappers-->
    </mappers>
    
    
</configuration>
