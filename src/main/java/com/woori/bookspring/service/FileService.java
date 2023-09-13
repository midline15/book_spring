package com.woori.bookspring.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Log
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

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if (deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제 했음");
        } else {
            log.info("파일이 없음");
        }
    }
}
