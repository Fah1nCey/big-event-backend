package com.fafa.bigeventbackend.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * base64转换为MultipartFile
 */
public class Base64ToMultipartFile implements MultipartFile {

    private final byte[] fileContent;
    private final String fileName;
    private String contentType;

    public Base64ToMultipartFile(byte[] fileContent, String fileName, String contentType) {
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() {
        return fileContent;
    }

    @Override
    public java.io.InputStream getInputStream() {
        return new java.io.ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(java.io.File dest) throws java.io.IOException {
        java.nio.file.Files.write(dest.toPath(), fileContent);
    }
}