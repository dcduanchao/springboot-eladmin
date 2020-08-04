package me.zhengjie.mongo.primary.demo.dao.impl;

import me.zhengjie.mongo.primary.demo.dao.DemoTestMongoDao;
import me.zhengjie.mongo.primary.demo.domain.DemoTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Author     ：duanchao
 * @ Date       ： 14:57 2020/7/27
 * @ Description：
 */
@Component
public class DemoTestDaoImpl  implements DemoTestMongoDao {

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate fristMongoTemplate;


    @Override
    public List<DemoTest> findByName(String name) {

        Criteria c = new Criteria();
        c.and("username").is(name);
        Query query = new Query(c);

        return fristMongoTemplate.find(query,DemoTest.class);
    }
}