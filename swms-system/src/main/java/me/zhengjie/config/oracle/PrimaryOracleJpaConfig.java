package me.zhengjie.config.oracle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        entityManagerFactoryRef = "entityOracleManagerFactoryPrimary",
        transactionManagerRef = "transactionOracleManagerPrimary",
        basePackages = {"me.zhengjie.oracle.primary"}) //设置Repository所在位置
public class PrimaryOracleJpaConfig {


    private static final Logger log = LoggerFactory.getLogger(PrimaryOracleJpaConfig.class);

    @Autowired
//    @Qualifier("primaryJpaProperties")
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    @Qualifier("primaryOracleDataSource")
    private DataSource primaryOracleDataSource;



    @Bean(name = "entityOracleManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityOracleManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryOracleDataSource)// 设置数据源
                .properties(jpaProperties.getProperties())// 设置jpa配置
                .properties(getVendorProperties())// 设置hibernate配置
                .packages("me.zhengjie.oracle.primary") //设置实体类所在位置
                .persistenceUnit("primaryOraclePersistenceUnit")// 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .build();
    }

    private Map getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());

    }

    @Bean(name = "transactionOracleManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }

}