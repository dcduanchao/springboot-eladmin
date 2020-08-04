package me.zhengjie.config.data;

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

/**
 * @author
 * @Description: 第二数据源配置
 * @date
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecond",
        transactionManagerRef = "transactionManagerSecond",
        basePackages = {"me.zhengjie.mysql.second.*"}) //设置Repository所在位置
public class SecondJpaConfig {
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;

    @Bean(name = "secondEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactorySecond(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactorySecond")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondDataSource)
                .properties(jpaProperties.getProperties())// 设置jpa配置
                .properties(getVendorProperties())
                .packages("me.zhengjie.mysql.second.*") //设置实体类所在位置
                .persistenceUnit("secondPersistenceUnit")
                .build();
    }

    private Map getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "transactionManagerSecond")
    public PlatformTransactionManager transactionManagerSecond(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySecond(builder).getObject());
    }
}