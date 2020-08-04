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
package me.zhengjie.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.service.TestTableService;
import me.zhengjie.service.dto.TestTableQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @website https://el-admin.vip
* @author dc
* @date 2020-07-24
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "门禁管理")
@RequestMapping("/api/testTable")
public class TestTableController {

    private final TestTableService testTableService;

    @GetMapping(value = "/list")
    @Log("查询门禁")
    @ApiOperation("查询门禁")
    public ResponseEntity<Object> query(TestTableQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testTableService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/list1")
    @Log("查询门禁")
    @ApiOperation("查询门禁")
    public ResponseEntity<Object> query1(TestTableQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testTableService.queryAll1(criteria,pageable),HttpStatus.OK);
    }


    @GetMapping(value = "/name")
    @Log("查询门禁")
    @ApiOperation("查询门禁")
    public ResponseEntity<Object> name(TestTableQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testTableService.selectByName(),HttpStatus.OK);
    }


}