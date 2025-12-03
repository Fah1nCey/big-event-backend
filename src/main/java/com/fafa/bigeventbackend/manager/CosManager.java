package com.fafa.bigeventbackend.manager;

import com.fafa.bigeventbackend.config.CosClientConfig;
import com.fafa.bigeventbackend.contant.FileConstant;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Cos 对象存储操作
 * 管理与腾讯云COS（对象存储）的交互
 * 为了封装, 在业务层中直接使用CosManager即可，不需要关心CosClient的具体实现
 *
 */
@Component
@Slf4j
public class CosManager {

    @Autowired
    private COSClient cosClient;

    @Resource
    private CosClientConfig cosClientConfig;

    /**
     * 上传对象（基于字符串路径）
     * 用于将本地文件上传到COS
     *
     * @param key           文件在存储桶中的唯一标识 Avatar_upload/xxx.jpg
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

    /**
     * 删除对象（基于字符串路径）
     * 用于删除COS上已上传的对象
     * @param filePath 已上传的对象路径，字符串形式
     * key: 文件在存储桶中的唯一标识 Avatar_upload/xxx.jpg
     */
    public void deleteObject(String filePath) {
        String key = extractKeyFromUrl(filePath);
        try {
            cosClient.deleteObject(cosClientConfig.getBucket(), key);
            log.info("删除文件成功：{}", filePath);
        } catch (CosClientException e) {
            log.error("删除文件失败：{}", e.getMessage());
        }
    }

    /**
     * 从URL中提取出COS上对象的唯一键
     * @param url
     * @return
     */
    public String extractKeyFromUrl(String url) {
        String domainPrefix = FileConstant.COS_HOST +  "/";
        return url.replace(domainPrefix, "");
    }
}
