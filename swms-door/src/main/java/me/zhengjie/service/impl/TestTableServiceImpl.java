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
package me.zhengjie.service.impl;

import me.zhengjie.mapper.mysql.primary.UserMapper;
import me.zhengjie.mysql.primary.entity.basic.domain.User;
import me.zhengjie.mysql.primary.entity.door.domain.TestTable;
import me.zhengjie.mysql.second.entity.domain.PublicationStatus;
import me.zhengjie.mysql.second.entity.repository.PublicationStatusRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.mysql.primary.entity.door.repository.TestTableRepository;
import me.zhengjie.service.TestTableService;
import me.zhengjie.service.dto.TestTableDto;
import me.zhengjie.service.dto.TestTableQueryCriteria;
import me.zhengjie.service.mapstruct.TestTableMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author dc
* @date 2020-07-24
**/
@Service
@RequiredArgsConstructor
public class TestTableServiceImpl implements TestTableService {

    private final TestTableRepository testTableRepository;
    private final TestTableMapper testTableMapper;
    private final PublicationStatusRepository publicationStatusRepository;

    private  final UserMapper userMapper;



    @Override
    public Map<String,Object> queryAll(TestTableQueryCriteria criteria, Pageable pageable){
        Page<TestTable> page = testTableRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(testTableMapper::toDto));
    }

    @Override
    public List<TestTableDto> queryAll(TestTableQueryCriteria criteria){
        return testTableMapper.toDto(testTableRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TestTableDto findById(Integer id) {
        TestTable testTable = testTableRepository.findById(id).orElseGet(TestTable::new);
        ValidationUtil.isNull(testTable.getId(),"TestTable","id",id);
        return testTableMapper.toDto(testTable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestTableDto create(TestTable resources) {
        return testTableMapper.toDto(testTableRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TestTable resources) {
        TestTable testTable = testTableRepository.findById(resources.getId()).orElseGet(TestTable::new);
        ValidationUtil.isNull( testTable.getId(),"TestTable","id",resources.getId());
        testTable.copy(resources);
        testTableRepository.save(testTable);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            testTableRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TestTableDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestTableDto testTable : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", testTable.getName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<PublicationStatus> queryAll1(TestTableQueryCriteria criteria, Pageable pageable) {
        List<PublicationStatus> all = publicationStatusRepository.findAll();
        return  all;
    }

    @Override
    public List<User> selectByName() {
        return userMapper.selectByName("admin");
    }
}