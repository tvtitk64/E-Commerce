package com.team2.fsoft.Ecommerce.service;

import io.minio.messages.Bucket;

import java.util.List;

public interface UploadService {
    String uploadFile(String name, byte[] content);
    byte[] getFile(String key);
    List<String> getAllBuckets();
}
