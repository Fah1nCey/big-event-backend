package com.fafa.bigeventbackend.service;

import com.fafa.bigeventbackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fafa.bigeventbackend.model.request.UpdateUserInfoRequest;
import com.fafa.bigeventbackend.model.request.UpdateUserPwdRequest;

/**
* @author 86156
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-11-19 09:39:52
*/
public interface UserService extends IService<User> {

    void register(String username, String password);

    User findByUserName(String username);

    void updateUserInfo(UpdateUserInfoRequest user);

    void updateAvatar(String avatarUrl);

    void updatePwd(UpdateUserPwdRequest updatePwdRequest) throws Exception;
}
