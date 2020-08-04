package me.zhengjie.mongo.primary.demo.repository;

import me.zhengjie.mongo.primary.demo.domain.DemoTest;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ Author     ：duanchao
 * @ Date       ： 13:23 2020/7/27
 * @ Description：
 */
public interface  DemoTestRepository extends MongoRepository<DemoTest,String> {
}