package com.woori.bookspring.service;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    public String uploadFile(String uploadPath, String origName, byte[] fileData) throws Exception {

        UUID uuid = UUID.randomUUID();
        String extension = origName.substring(origName.lastIndexOf("."));
        String savedFileName = uuid + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

}
