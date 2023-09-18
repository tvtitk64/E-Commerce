package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.audit_log.GlobalSpringEvent;
import com.team2.fsoft.Ecommerce.service.UploadService;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UploadFileServiceImpl implements UploadService {
    private MinioClient minioClient;
    private ApplicationEventPublisher publisher;

    @Value("${minio.buckek.name}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    public UploadFileServiceImpl(MinioClient minioClient, ApplicationEventPublisher publisher) {
        this.minioClient = minioClient;
        this.publisher = publisher;
    }

    @Override
    public String uploadFile(String name, byte[] content) {

        try {

            InputStream inputStream = new ByteArrayInputStream(content);

            // Tải lên tệp tin lên Minio sử dụng InputStream
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(defaultBucketName)
                            .object(defaultBaseFolder + name)
                            .stream(inputStream, content.length, -1) // Sử dụng InputStream
                            .build()
            );

            // Tạo pre-signed URL cho tệp tin vừa tải lên
            String url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(defaultBucketName)
                                    .object(defaultBaseFolder + name)
                                    .expiry(7, TimeUnit.DAYS)
                                    .build());

         this.publisher.publishEvent(new GlobalSpringEvent<String>("Upload file "+name+" sucessfully!",true));
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public byte[] getFile(String key) {
        try {

            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(defaultBucketName)
                            .object(defaultBaseFolder + "/" + key)
                            .build());
            // Read data from stream


            byte[] content = IOUtils.toByteArray(stream);
            stream.close();
            this.publisher.publishEvent(new GlobalSpringEvent<String>("Download file "+key+"sucessfully",true));
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<String> getAllBuckets() {
        try {
            return minioClient.listBuckets().stream().map(bucket -> bucket.name()).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
