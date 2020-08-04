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
@MapperScan(basePackages = "me.zhengjie.mapper.oracle.second", sqlSessionTemplateRef  = "secondOracleSessionTemplate")
public class MybatisOraclesecondConfig {

    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondaryDataSource;

    @Bean(name = "secondaryOracleSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/oracle/second/**/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    @Bean(name = "secondaryOracleTransactionManager")
    public DataSourceTransactionManager secondaryTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "secondaryOracleSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondaryOracleSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}