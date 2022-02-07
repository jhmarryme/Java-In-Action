package com.jhmarryme.rabbit.producer.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
// 在 数据库脚本 配置类完成之后，再初始化该类
@AutoConfigureAfter(value = {RabbitProducerDataSourceConfiguration.class})
public class RabbitProducerMyBatisConfiguration {

	@Resource(name= "rabbitProducerDataSource")
	private DataSource rabbitProducerDataSource;
	
	@Bean(name="rabbitProducerSqlSessionFactory")
	public SqlSessionFactory rabbitProducerSqlSessionFactory(DataSource rabbitProducerDataSource) {
		
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(rabbitProducerDataSource);
        // 资源匹配器，将给定的路径转换为资源
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
            // 使用资源匹配器扫描到指定路径下的所有符合条件的资源
            // 这里设置的是 mapper.xml 的路径
			bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
			SqlSessionFactory sqlSessionFactory = bean.getObject();
			sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);
			return sqlSessionFactory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Bean(name="rabbitProducerSqlSessionTemplate")
	public SqlSessionTemplate rabbitProducerSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
