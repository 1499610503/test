package com.example.ruijisboot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileUploadController {

    @Autowired
    private ServletContext servletContext;

    @PostMapping("/upload")
    public R handleFileUpload(MultipartFile file) {
        System.out.println(file);
        if (file.isEmpty()) {
//            return "Please select a file to upload.";
            return R.error("Please select a file to upload.");
        }
        try {
            // 构建上传目录的路径
            String uploadDir = servletContext.getRealPath("/upload/test/");
            // 确保目录存在
            File dirPath = new File(uploadDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            // 构建上传文件的完整路径
            Path path = Paths.get(uploadDir).resolve(file.getOriginalFilename());
            System.out.println(path);
            // 保存文件
            Files.write(path, file.getBytes());
            // 构建文件在Web应用中的URL
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/upload/test/")
                    .path(file.getOriginalFilename())
                    .toUriString();
//            return "File uploaded successfully! You can download it from: " + fileUrl;
            return R.success(fileUrl,"成功");
        } catch (IOException e) {
            e.printStackTrace();
//            return "File upload failed!";
            return R.error("File upload failed!");
        }
    }
}
