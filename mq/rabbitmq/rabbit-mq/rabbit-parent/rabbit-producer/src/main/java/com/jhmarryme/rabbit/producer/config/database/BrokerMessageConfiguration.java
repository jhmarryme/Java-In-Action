package com.jhmarryme.rabbit.producer.config.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 	BrokerMessageConfiguration
 * 	帮我执行SQL脚本
 * 	帮我进行数据库表结构的创建
 * @author Jiahao Wang
 *
 */
@Configuration
public class BrokerMessageConfiguration {

    @Autowired
    private DataSource rabbitProducerDataSource;

    // 注意这里的 Resource 是 org.springframework.core.io.Resource 的
    @Value("classpath:rabbit-producer-message-schema.sql")
    private Resource schemaScript;

    /** 用于在初始化过程中设置数据库，并在销毁过程中清理数据库。 */
    @Bean
    public DataSourceInitializer initDataSourceInitializer() {
    	System.err.println("--------------rabbitProducerDataSource-----------:" + rabbitProducerDataSource);
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(rabbitProducerDataSource);
        // 在程序初始化阶段执行
        initializer.setDatabasePopulator(databasePopulator());
        // 这个则在程序销毁阶段执行
        // initializer.setDatabaseCleaner();
        return initializer;
    }

    /** 用于填充、初始化或清理数据库的策略 */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }
}
