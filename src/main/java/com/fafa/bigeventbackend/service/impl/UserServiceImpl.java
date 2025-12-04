package com.fafa.bigeventbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fafa.bigeventbackend.manager.CosManager;
import com.fafa.bigeventbackend.model.entity.User;
import com.fafa.bigeventbackend.model.request.UpdateUserInfoRequest;
import com.fafa.bigeventbackend.model.request.UpdateUserPwdRequest;
import com.fafa.bigeventbackend.service.UserService;
import com.fafa.bigeventbackend.mapper.UserMapper;
import com.fafa.bigeventbackend.utils.Md5Util;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author 86156
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-11-19 09:39:52
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CosManager cosManager;

    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.register(username, md5String);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoRequest user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        // 获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        // 根据id查询用户,获取原头像路径
        User user = this.getById(id);
        String userPic = user.getUserPic();
        // 根据id更新头像
        userMapper.updateAvatar(avatarUrl, id);
        // 更新头像成功后，删除原头像(前提是有头像)
        if (userPic != null) {
            cosManager.deleteObject(userPic);
        }
    }

    @Override
    public void updatePwd(UpdateUserPwdRequest updatePwdRequest) throws Exception {
        // 取出当前用户的密码
        Map<String, Object> map =  ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User loginUser = findByUserName(username);
        String loginUserPwd = loginUser.getPassword();
        Integer id = loginUser.getId();
        // 加密密码，判断与旧密码是否一致
        boolean b = Md5Util.checkPassword(updatePwdRequest.getOldPwd(), loginUserPwd);
        if (!b) {
            throw new Exception("旧密码错误");
        }
        // 判断新密码和确认密码是否一致
        if (!updatePwdRequest.getNewPwd().equals(updatePwdRequest.getRePwd())) {
            throw new Exception("两次密码输入不一致");
        }
        // 加密新密码
        String md5String = Md5Util.getMD5String(updatePwdRequest.getNewPwd());
        // 更新密码
        userMapper.updatePwd(id, md5String);
    }
}




