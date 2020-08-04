package me.zhengjie.config.oracle;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @ Author     ：duanchao
 * @ Date       ： 10:05 2020/7/28
 * @ Description：
 */
@Configuration
public class DataOracleSourceConfig {


    private static final Logger log = LoggerFactory.getLogger(DataOracleSourceConfig.class);


    @Bean(value = "primaryOracleDataSource")
    @ConfigurationProperties(prefix = "spring.oracle.primary")
    public DataSource dataOracleSourceOne() {
        log.info("Init DataOracleSourceOne");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(value = "secondOracleDataSource")
    @ConfigurationProperties(prefix = "spring.oracle.secondary")
    public DataSource dataOracleSourceTwo() {
        log.info("Init DataOracleSourceTwo");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "primaryOracleTemplate")
    public JdbcTemplate primaryTemplate(@Qualifier("primaryOracleDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryOracleTemplate")
    public JdbcTemplate secondaryTemplate(@Qualifier("secondOracleDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}