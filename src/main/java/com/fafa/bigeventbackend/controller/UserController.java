package com.fafa.bigeventbackend.controller;

import com.fafa.bigeventbackend.common.Result;
import com.fafa.bigeventbackend.model.entity.User;
import com.fafa.bigeventbackend.model.request.UpdateUserInfoRequest;
import com.fafa.bigeventbackend.model.request.UpdateUserPwdRequest;
import com.fafa.bigeventbackend.service.UserService;
import com.fafa.bigeventbackend.utils.JwtUtil;
import com.fafa.bigeventbackend.utils.Md5Util;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已被占用");
        }
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
            return Result.error("密码错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", loginUser.getId());
        map.put("username", loginUser.getUsername());
        String token = JwtUtil.genToken(map);
        return Result.success(token);
    }

    // 获取用户详细信息(不使用拦截器版)
//    @GetMapping("/userInfo")
//    public Result<User> getUserInfo(@RequestHeader("Authorization") String token) {
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String)map.get("username");
//        User user = userService.findByUserName(username);
//        return Result.success(user);
//    }

    // 获取用户详细信息
    @GetMapping("/userInfo")
    public Result<User> getUserInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    // 更新用户基本信息
    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody @Validated UpdateUserInfoRequest user) {
        // 参数校验
        // 更新信息
        userService.updateUserInfo(user);
        // 返回结果
        return Result.success();
    }

    // 更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    // 更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody @Validated UpdateUserPwdRequest updatePwdRequest) throws Exception {
        userService.updatePwd(updatePwdRequest);
        return Result.success();
    }















}
