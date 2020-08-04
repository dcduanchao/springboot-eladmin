package me.zhengjie.config.data;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @ Author     ：duanchao
 * @ Date       ： 15:43 2020/7/24
 * @ Description：
 */
@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Primary
    @Bean(value = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.primary")
    public DataSource dataSourceOne(){
        log.info("Init DataSourceOne");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(value = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.secondary")
    public DataSource dataSourceTwo(){
        log.info("Init DataSourceTwo");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "primaryMysqlTemplate")
    public JdbcTemplate primaryTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryMysqlTemplate")
    public JdbcTemplate secondaryTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}