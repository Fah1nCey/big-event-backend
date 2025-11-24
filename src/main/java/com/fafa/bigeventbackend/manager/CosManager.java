package com.fafa.bigeventbackend.manager;

import com.fafa.bigeventbackend.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Cos 对象存储操作
 * 管理与腾讯云COS（对象存储）的交互
 *
 */
@Component
public class CosManager {

    @Autowired
    private COSClient cosClient;

    @Resource
    private CosClientConfig cosClientConfig;

    /**
     * 上传对象（基于字符串路径）
     * 用于将本地文件上传到COS
     *
     * @param key           唯一键
     * @param localFilePath 本地文件路径 ,字符串形式
     * @return
     */
    public PutObjectResult putObject(String key, String localFilePath) {
        // 用于封装上传请求的相关信息
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                new File(localFilePath));
        // 将文件上传至COS，并返回上传结果
        return cosClient.putObject(putObjectRequest);
    }
}
