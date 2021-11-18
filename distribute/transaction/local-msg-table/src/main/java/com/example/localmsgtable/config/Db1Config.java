package com.example.localmsgtable.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@MapperScan(value = "com.example.localmsgtable.db1.dao", sqlSessionFactoryRef = "sqlSessionFactoryBean1")
@Configuration
public class Db1Config {

    public static final String DB_PWD = "jgUOAW3WOFOnOGbPEew+xtrPvZtTv/BD";

    @Autowired
    private StringEncryptor stringEncryptor;

    @Bean("db1")
    public DataSource db1() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword(stringEncryptor.decrypt(Db1Config.DB_PWD));
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
    @Bean("tm1")
    public PlatformTransactionManager transactionManager(@Qualifier("db1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
