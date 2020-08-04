package me.zhengjie.config.mongo;

import com.mongodb.MongoClientURI;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @ Author     ：duanchao
 * @ Date       ： 13:08 2020/7/27
 * @ Description：
 */

@Configuration
@EnableMongoRepositories(basePackages = {"me.zhengjie.mongo.primary"},
                mongoTemplateRef = "primaryMongoTemplate")
public class PrimaryMongoConfig {


    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.data.mongodb.primary")
    public MongoProperties primaryMongoProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() throws Exception {
        return new MongoTemplate(primaryFactory(primaryMongoProperties()));
    }

    @Bean
    @Primary
    public MongoDbFactory primaryFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoDbFactory(new MongoClientURI(primaryMongoProperties().getUri()));
    }
}