package com.amigos.awsuploadimage.service.impl;

import com.amigos.awsuploadimage.constants.ErrorMessages;
import com.amigos.awsuploadimage.exceptions.UserException;
import com.amigos.awsuploadimage.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${application.file.filesPath}")
    private String filesPath;

    @Override
    public String store(MultipartFile file, String path) {
        String uploadFileName = file.getOriginalFilename();
        String savePath = filesPath + path;
        System.out.println("*** " + savePath + " ***");
        File pathAsFile = new File(savePath);
        if(!Files.exists(Paths.get(savePath))){
            pathAsFile.mkdirs();
        }

        try {
            Path saveDestination = Paths.get(savePath)
                    .resolve(uploadFileName)
                    .normalize().toAbsolutePath();
            FileCopyUtils.copy(file.getBytes(), saveDestination.toFile());
            return uploadFileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException(ErrorMessages.errorWhileSavingFile);
        }
    }
}
