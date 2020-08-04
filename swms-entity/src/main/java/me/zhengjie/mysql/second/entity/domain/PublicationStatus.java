package me.zhengjie.mysql.second.entity.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* @author sheryl
* @date 2020-03-31
*/
@Entity
@Data
@Table(name="publication_status")
public class PublicationStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 企业ID */
    @Column(name = "company_id")
    private Integer companyId;

    /** 企业名称 */
    @Column(name = "company_name")
    private String companyName;

    /** 数据期数 */
    @Column(name = "data_no")
    private String dataNo;

    /** 统计公报 */
    @Column(name = "tongjigongbao")
    private Integer tongjigongbao;

    /** 预决算报告 */
    @Column(name = "yujuesuan")
    private Integer yujuesuan;

    /** 评级报告 */
    @Column(name = "pingjibaogao")
    private Integer pingjibaogao;

    /** 审计报告 */
    @Column(name = "shenjibaogao")
    private Integer shenjibaogao;

    /** 募集说明书 */
    @Column(name = "mujishuomingshu")
    private Integer mujishuomingshu;

    /** 财务报告 */
    @Column(name = "caiwubaobiao")
    private Integer caiwubaobiao;

    /** 偿付能力 */
    @Column(name = "changfunengli")
    private Integer changfunengli;

    /** 年度报告 */
    @Column(name = "niandubaogao")
    private Integer niandubaogao;

    /** 年度信息披露 */
    @Column(name = "nianduxinxipilu")
    private Integer nianduxinxipilu;

    /** 债券年度 */
    @Column(name = "zhaiquanniandu")
    private Integer zhaiquanniandu;

    /** 预留资料1 */
    @Column(name = "yuliuziliao1")
    private Integer yuliuziliao1;

    /** 预留资料2 */
    @Column(name = "yuliuziliao2")
    private Integer yuliuziliao2;

    public void copy(PublicationStatus source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}