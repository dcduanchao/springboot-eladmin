package me.zhengjie.mongo.secondary.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Author     ：duanchao
 * @ Date       ： 13:19 2020/7/27
 * @ Description：
 */

@Data
@Document(collection = "demo_test_s")
public class DemoTestSecond implements Serializable {

    @Id
    private  String id;

    /** 用户名 */
    private String username;

    /** 年龄 */
    private Integer age;

    @CreatedDate
    private Date createTime;
}