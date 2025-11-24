package com.fafa.bigeventbackend.mapper;

import com.fafa.bigeventbackend.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fafa.bigeventbackend.model.request.UpdateUserInfoRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

/**
* @author 86156
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-11-19 09:39:52
* @Entity com.fafa.bigeventbackend.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into user (username, password, create_time, update_time) values (#{username}, #{password}, now(), now())")
    void register(@Param("username") String username, @Param("password") String password);

    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    @Update("update user set nickname = #{nickname}, email = #{email}, update_time = now() where id = #{id}")
    void updateUserInfo(UpdateUserInfoRequest user);

    @Update("update user set user_pic = #{avatarUrl}, update_time = now() where id = #{id}")
    void updateAvatar(@Param("avatarUrl") String avatarUrl, @Param("id") Integer id);

    @Update("update user set password = #{password}, update_time = now() where id = #{id}")
    void updatePwd(@Param("id") Integer id, @Param("password") String password);
}




