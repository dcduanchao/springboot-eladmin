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
@MapperScan(basePackages = "me.zhengjie.mapper.mysql.primary", sqlSessionTemplateRef  = "primaryMySqlSessionTemplate")
public class MybatisMysqlPrimaryConfig {


    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Bean(name = "primaryMySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/sql/primary/**/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean(name = "primaryMySqlTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "primaryMySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primaryMySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}