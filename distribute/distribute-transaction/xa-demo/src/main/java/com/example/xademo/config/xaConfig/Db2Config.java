package com.example.xademo.config.xaConfig;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *
 * @author JiaHao Wang
 */
@MapperScan(value = "com.example.xademo.db2.dao", sqlSessionFactoryRef = "sqlSessionFactoryBean2")
@Configuration
public class Db2Config {

    @Bean("db2")
    public DataSource db1() {
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUser("root");
        xaDataSource.setPassword("jh541224");
        xaDataSource.setUrl("jdbc:mysql://1.14.140.53:30012/xa_2");

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        return atomikosDataSourceBean;
    }

    @Bean("sqlSessionFactoryBean2")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("db2") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("mybatis/db2/*.xml"));
        return sqlSessionFactoryBean;
    }
}
