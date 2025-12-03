package com.fafa.bigeventbackend.controller;

import com.fafa.bigeventbackend.common.Result;
import com.fafa.bigeventbackend.manager.CosManager;
import com.fafa.bigeventbackend.model.entity.User;
import com.fafa.bigeventbackend.model.request.*;
import com.fafa.bigeventbackend.service.UserService;
import com.fafa.bigeventbackend.utils.Base64ToMultipartFile;
import com.fafa.bigeventbackend.utils.JwtUtil;
import com.fafa.bigeventbackend.utils.Md5Util;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadController fileUploadController;

    @Autowired
    private CosManager cosManager;

    /**
     * 注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        // TODO: 2025/11/25 优化、重复密码校验
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已被占用");
        }
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody @Validated UserLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
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
        // 将 token 字符串包装到 Map 中
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return Result.success(data);
    }

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

    /**
     * 更新用户头像
     * description: 前端传来的头像是 base64 编码的，需要先解码，然后转为 MultipartFile 上传到 COS，再更新数据库
     * @param updateAvatarRequest
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody UpdateAvatarRequest updateAvatarRequest) {
        String avatarBase64 = updateAvatarRequest.getAvatar();

        if (avatarBase64 == null || !avatarBase64.contains(",")) {
            return Result.error("非法的 Base64 数据");
        }

        // 提取 mime type
        String prefix = avatarBase64.substring(0, avatarBase64.indexOf(","));
        String contentType = prefix.substring(prefix.indexOf(":") + 1, prefix.indexOf(";"));
        String suffix = contentType.substring(contentType.indexOf("/") + 1);

        // 提取 base64 数据
        String base64Data = avatarBase64.substring(avatarBase64.indexOf(",") + 1);

        // ---------- 2. 校验是否是图片 ----------
        if (!contentType.startsWith("image/")) {
            return Result.error("上传的图片格式不正确");
        }

        // 解码
        byte[] bytes = Base64.getDecoder().decode(base64Data);

        // 生成唯一文件名
        String fileName = UUID.randomUUID() + "." + suffix;

        MultipartFile file = new Base64ToMultipartFile(bytes, fileName, contentType);

        // 调用 upload 方法（参数 MultipartFile）
        String url = fileUploadController.upload(file);
        try {
            userService.updateAvatar(url);
        } catch (Exception e) {
            cosManager.deleteObject(url);
            throw e;
        }
        return Result.success();
    }

    // 更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody @Validated UpdateUserPwdRequest updatePwdRequest) throws Exception {
        userService.updatePwd(updatePwdRequest);
        return Result.success();
    }















}
