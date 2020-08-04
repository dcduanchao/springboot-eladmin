package me.zhengjie.mysql.second.entity.dao.impl;

import me.zhengjie.mysql.second.entity.dao.PublicationStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：duanchao
 * @ Date       ： 10:42 2020/7/28
 * @ Description：
 */

@Component
public class PublicationStatusDaoImpl implements PublicationStatusDao {

  @Autowired
  @Qualifier("secondaryMysqlTemplate")
  private JdbcTemplate secondaryMysqlTemplate;


    @Override
    public long findCount() {
        String sql = "SELECT COUNT(1) FROM publication_status";
        Integer count = secondaryMysqlTemplate.queryForObject(sql, Integer.class);
        return count;
    }
}