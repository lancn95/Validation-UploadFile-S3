package com.amigos.awsuploadimage.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String store(MultipartFile file, String path);
}
