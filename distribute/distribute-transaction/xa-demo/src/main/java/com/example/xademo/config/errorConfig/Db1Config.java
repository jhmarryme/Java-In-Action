package com.example.xademo.config.errorConfig;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *
 * @author JiaHao Wang
 */
@MapperScan(value = "com.example.xademo.db1.dao", sqlSessionFactoryRef = "sqlSessionFactoryBean1")
// @Configuration
public class Db1Config {

    @Bean("db1")
    public DataSource db1() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("jh541224");
        dataSource.setUrl("jdbc:mysql://1.14.140.53:30011/xa_1");
        return dataSource;
    }

    @Bean("sqlSessionFactoryBean1")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("db1") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("mybatis/db1/*.xml"));
        return sqlSessionFactoryBean;
    }
    // @Bean("tm1")
    // public PlatformTransactionManager transactionManager(@Qualifier("db1") DataSource dataSource) {
    //     return new DataSourceTransactionManager(dataSource);
    // }
}
