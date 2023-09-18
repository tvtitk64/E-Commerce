package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.UploadService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileProcessController {
    final UploadService uploadService;

    public FileProcessController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping(path = "/buckets")
    public List<String> listBuckets() {
        return uploadService.getAllBuckets();

    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestPart(value = "file", required = false) MultipartFile files) throws IOException {
        return uploadService.uploadFile(files.getOriginalFilename(), files.getBytes());

    }

    @GetMapping(path = "/download")
    public MessagesResponse uploadFile(@RequestParam(value = "file") String file) throws IOException {
        MessagesResponse ms = new MessagesResponse();
        try {
            byte[] data = uploadService.getFile(file);
            ByteArrayResource resource = new ByteArrayResource(data);
            ms.data = resource;
        } catch (Exception ex) {
            ms.code = 404;
            ms.message = "File Not Found!";
        }
        return ms;
    }
}
