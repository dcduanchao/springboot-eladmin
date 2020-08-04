package me.zhengjie.config.oracle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerOracleFactorySecond",
        transactionManagerRef = "transactionOracleManagerSecond",
        basePackages = {"me.zhengjie.oracle.second"}) //设置Repository所在位置
public class SecondOracleJpaConfig {
    @Autowired
//    @Qualifier("primaryJpaProperties")
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    @Qualifier("secondOracleDataSource")
    private DataSource secondOracleDataSource;



    @Bean(name = "secondOracleEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerOracleFactorySecond")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondOracleDataSource)// 设置数据源
                .properties(jpaProperties.getProperties())// 设置jpa配置
                .properties(getVendorProperties())// 设置hibernate配置
                .packages("me.zhengjie.oracle.second") //设置实体类所在位置
                .persistenceUnit("secondOraclePersistenceUnit")// 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .build();
    }

    private Map getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());

    }

    @Bean(name = "transactionOracleManagerSecond")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }

}