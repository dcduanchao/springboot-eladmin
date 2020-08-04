package me.zhengjie.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @ Author     ：duanchao
 * @ Date       ： 9:48 2020/7/29
 * @ Description：
 */
@Configuration
@MapperScan(basePackages = "me.zhengjie.mapper.oracle.primary", sqlSessionTemplateRef  = "primaryOracleSessionTemplate")
public class MybatisOraclePrimaryConfig {


    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Bean(name = "primaryOracleSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/oracle/primary/**/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean(name = "primaryOracleTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "primaryOracleSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primaryOracleSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}