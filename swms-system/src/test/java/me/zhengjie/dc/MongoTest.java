package me.zhengjie.dc;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import me.zhengjie.mapper.mysql.primary.UserMapper;
import me.zhengjie.mongo.primary.demo.dao.DemoTestMongoDao;
import me.zhengjie.mongo.primary.demo.domain.DemoTest;
import me.zhengjie.mongo.primary.demo.repository.DemoTestRepository;
import me.zhengjie.mongo.secondary.demo.domain.DemoTestSecond;
import me.zhengjie.mongo.secondary.demo.repository.DemoTestSecondRepository;
import me.zhengjie.mysql.primary.entity.basic.domain.User;
import me.zhengjie.mysql.primary.entity.door.domain.TestTable;
import me.zhengjie.mysql.primary.entity.door.repository.TestTableRepository;
import me.zhengjie.mysql.second.entity.dao.PublicationStatusDao;
import me.zhengjie.oracle.primary.demo.domain.TestOracle;
import me.zhengjie.oracle.primary.demo.repository.TestOracleRepository;
import oracle.sql.BLOB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoTest {


    @Autowired
    private DemoTestRepository demoTestRepository;

    @Autowired
    private DemoTestSecondRepository demoTestSecondRepository;

    @Autowired
    @Qualifier("primaryMongoTemplate")
    private MongoTemplate fristMongoTemplate;


    @Autowired
    @Qualifier("secondaryMongoTemplate")
    private MongoTemplate secondMongoTemplate;

    @Autowired
    private DemoTestMongoDao demoTestMongoDao;

    @Autowired
    private PublicationStatusDao publicationStatusDao;


    @Autowired
    private TestOracleRepository testOracleRepository;


    @Autowired
    @Qualifier("primaryOracleTemplate")
    private JdbcTemplate primaryOracleTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestTableRepository testTableRepository;

    @Test
    public  void  mysqlTemp(){
//        long count = publicationStatusDao.findCount();
//        System.out.println(count);

//        User user = userMapper.selectById(1L);
//        System.out.println(JSONObject.toJSONString(user));

        int admin1 = userMapper.findCountByName("admin");
        System.out.println(admin1);
        List<User> admin = userMapper.selectByName("admin");


        System.out.println(JSONObject.toJSONString(admin));
    }

    @Test
    public  void  oracleTemp(){
//        List<TestOracle> list = new ArrayList<>();
//        for (int i = 0;i<5;i++){
//            TestOracle demoTest = new TestOracle();
//            demoTest.setId(UUID.randomUUID().toString());
//            demoTest.setName(""+i);
//
//
//            list.add(demoTest);
//        }
//
//        testOracleRepository.saveAll(list);

        String sql ="SELECT * FROM TEST_ORACLE ";
        List<Map<String, Object>> maps = primaryOracleTemplate.queryForList(sql);
        System.out.println(JSONObject.toJSONString(maps));

        TestOracle testOracle = testOracleRepository.findById("1").get();
        System.out.println(testOracle);
    }


    @Test
    public void primaryTest() {

        List<DemoTest> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            DemoTest demoTest = new DemoTest();
            demoTest.setUsername(""+i);
            demoTest.setAge(i);
            demoTest.setCreateTime(new Date());
            list.add(demoTest);
        }
        demoTestRepository.saveAll(list);

        List<DemoTest> all = demoTestRepository.findAll();

        System.out.println(JSONObject.toJSONString(all));

    }


    @Test
    public  void  tempTest(){
//        List<DemoTest> all = fristMongoTemplate.findAll(DemoTest.class);


        List<DemoTest> byName = demoTestMongoDao.findByName("1");
        System.out.println(JSONObject.toJSONString(byName));
    }

    @Test
    public void secondTest() {

        List<DemoTestSecond> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            DemoTestSecond demoTest = new DemoTestSecond();
            demoTest.setUsername(""+i);
            demoTest.setAge(i);
            demoTest.setCreateTime(new Date());
            list.add(demoTest);
        }
        demoTestSecondRepository.saveAll(list);

        List<DemoTestSecond> all = demoTestSecondRepository.findAll();

        System.out.println(JSONObject.toJSONString(all));

    }


    @Test
    public void  pic() throws Exception {
        File file = new File("C:\\Users\\R480\\Desktop\\docker容器结构.jpg");


        InputStream inputStream = new FileInputStream(file);
        int available = inputStream.available();
        byte[] data = new byte[available];
        inputStream.read(data);
        inputStream.close();


        String picS = Base64Utils.encodeToString(data);
        TestTable demoTest = new TestTable();
        demoTest.setId(0);
        demoTest.setName("");

        demoTest.setPic(data);
        demoTest.setPicStr(picS);



        testTableRepository.save(demoTest);

        TestTable testTable = testTableRepository.findById(3).get();

        System.out.println(testTable.toString());


    }
}
