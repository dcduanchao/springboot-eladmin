package me.zhengjie.mongo.secondary.demo.repository;

import me.zhengjie.mongo.secondary.demo.domain.DemoTestSecond;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ Author     ：duanchao
 * @ Date       ： 13:23 2020/7/27
 * @ Description：
 */
public interface DemoTestSecondRepository extends MongoRepository<DemoTestSecond,String> {
}