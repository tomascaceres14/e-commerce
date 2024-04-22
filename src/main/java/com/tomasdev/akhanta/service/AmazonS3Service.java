package com.tomasdev.akhanta.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

    String upload(MultipartFile image, String folder);

    String update(MultipartFile image, String folder, String filename);

    void delete(String folder, String filename);

}
