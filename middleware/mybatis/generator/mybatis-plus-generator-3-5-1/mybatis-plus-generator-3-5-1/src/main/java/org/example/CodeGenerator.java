package org.example;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author JiaHao Wang
 * @date 2022/6/2 上午10:43
 */
public class CodeGenerator {

    @AllArgsConstructor
    @Getter
    public enum DataSourceType {
        TEST("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=)(PORT=))(CONNECT_DATA=" +
                "(SERVICE_NAME=travelweb)))",
                "",
                "",
                "TRAVELWEB");
        private final String url;
        private final String username;
        private final String password;
        private final String schema;
    }

    public void create(DataSourceType dataSourceType) {
        System.out.println(System.getProperty("user.dir"));
        FastAutoGenerator generator = FastAutoGenerator
                .create(buildDataSource(dataSourceType))
                .globalConfig(
                        builder -> builder.author("wjh")
                                // .enableSwagger()
                                .dateType(DateType.TIME_PACK)
                                .outputDir("src/main/java"))
                .packageConfig(
                        builder -> builder.parent("com.example")
                                .moduleName("sys")
                                .entity("po")
                                .mapper("mapper")
                                .xml("mapper.xml"))
                .strategyConfig(
                        builder -> builder.addInclude("ETERMCLOUD_PHONE_USER")
                                .enableSchema()
                                .entityBuilder().enableLombok().enableTableFieldAnnotation()
                                .mapperBuilder().enableMapperAnnotation().enableBaseResultMap().enableBaseColumnList()
                )
                .templateEngine(new FreemarkerTemplateEngine());

        generator.execute();

    }


    private DataSourceConfig.Builder buildDataSource(DataSourceType dataSourceType) {
        DataSourceConfig.Builder builder = new DataSourceConfig
                .Builder(dataSourceType.getUrl(), dataSourceType.getUsername(), dataSourceType.getPassword());
        if (StringUtils.isNotEmpty(dataSourceType.getSchema())) {
            builder = builder.schema(dataSourceType.getSchema());
        }
        return builder;
    }

}
