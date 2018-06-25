package com.api.service;

import com.api.repository.StorageRepository;
import com.api.model.Storage;
import com.api.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    @Value("${upload.file.storage.directory}")
    private String storageDirectory;

    @Autowired
    StorageRepository storageRepository;

    static int M = 1024 * 1024;

    public Resource loadAsResource(String filename) {
        try {
            String filePath = filename.substring(0, 2) + "/" + filename.substring(2);
            Resource resource = new UrlResource(Paths.get(storageDirectory, filePath).toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);

            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public Storage store(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("Failed to store empty file " + file.getOriginalFilename());
        }

        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        byte[] bytes;
        if (file.getSize() > M) {
            bytes = ImageUtil.compress(file.getInputStream(), fileType, 1920, 1920);
        } else {
            bytes = file.getBytes();
        }

        String contentHash = DigestUtils.md5DigestAsHex(bytes);
        Storage oldStorage = storageRepository.findByFileHash(contentHash);
        if (oldStorage != null) return oldStorage;

        try {
            Path destDir = Paths.get(storageDirectory, contentHash.substring(0, 2));
            if (!Files.exists(destDir)) Files.createDirectory(destDir);

            // String filePath = contentHash.substring(0, 2) + "/" + contentHash.substring(2) + "." + fileType;
            Storage storage = new Storage();
            storage.setFileHash(contentHash);
            storage.setFileName(file.getName());
            storage.setFileType(file.getContentType());
            storage.setFileSize(file.getSize());
            storage.setOriginalFileName(file.getOriginalFilename());

            return storageRepository.save(storage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
