package me.zhengjie.mongo.primary.demo.dao;

import me.zhengjie.mongo.primary.demo.domain.DemoTest;

import java.util.List;

/**
 * @ Author     ：duanchao
 * @ Date       ： 14:59 2020/7/27
 * @ Description：
 */
public interface DemoTestMongoDao {


    List<DemoTest> findByName(String name);
}
