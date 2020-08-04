/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.mysql.primary.entity.door.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
* @website https://el-admin.vip
* @description /
* @author dc
* @date 2020-07-24
**/
@Entity
@Data
@Table(name="test_table")
public class TestTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "zhz主键")
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称")
    private String name;

    @Column(name = "pic")
    @ApiModelProperty(value = "图片")
    private byte[] pic;

    @Column(name = "pic_str")
    @ApiModelProperty(value = "图片")
    private String picStr;

    public void copy(TestTable source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}