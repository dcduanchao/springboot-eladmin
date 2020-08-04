package me.zhengjie.mapper.mysql.primary;

import me.zhengjie.mysql.primary.entity.basic.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Author     ：duanchao
 * @ Date       ： 10:39 2020/7/29
 * @ Description：
 */
@Component
@Mapper
public interface UserMapper {

    @Select(value = " SELECT * FROM sys_user WHERE user_id = #{id}")
    User selectById(@Param("id") Long id);



    List<User> selectByName(@Param("name")String name);

    int findCountByName(@Param("name")String name);
}