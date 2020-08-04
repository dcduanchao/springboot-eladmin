package me.zhengjie.oracle.primary.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ Author     ：duanchao
 * @ Date       ： 13:56 2020/7/28
 * @ Description：
 */
@Entity
@Data
@Table(name = "TEST_ORACLE")
public class TestOracle  {

    @Id
//    @GeneratedValue
    private String id;

    private  String name;
}