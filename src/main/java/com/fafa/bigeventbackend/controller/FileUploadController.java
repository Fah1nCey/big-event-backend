package com.fafa.bigeventbackend.controller;

import com.fafa.bigeventbackend.contant.FileConstant;
import com.fafa.bigeventbackend.manager.CosManager;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Resource
    private CosManager cosManager;

    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile multipartFile){
        // 获取当前用户的id，用于存储到cos中的路径
        Map<String, Object> userMap = ThreadLocalUtil.get();
        Integer userId = (Integer)userMap.get("id");
        // 保证文件名唯一，防止文件覆盖
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = "";
        if (originalFilename.contains(".")) {
            // 获取文件后缀 如.jpg
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 使用 UUID + 后缀，保证唯一又短
        String fileName = UUID.randomUUID() + suffix;
        String filePath = "File_upload/" + userId + "/" + fileName;

        File tempFile = null;
        try {
            // 创建一个临时文件来存储上传的内容(因为cosManager.putObject方法需要一个本地文件路径)
            tempFile = File.createTempFile(filePath, null);
            // 将上传的文件写入临时文件中
            multipartFile.transferTo(tempFile);
            cosManager.putObject(filePath, tempFile.getAbsolutePath());
            return FileConstant.COS_HOST + "/" + filePath;
        } catch (IOException e) {
            return "上传失败";
        } finally {
            if (tempFile != null) {
                // 删除临时文件
                tempFile.delete();
            }
        }
    }


}
