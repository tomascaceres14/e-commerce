package com.tomasdev.akhanta.service.impl;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.service.AmazonS3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final AmazonS3 amazonS3;

    @Override
    public String upload(MultipartFile image, String folder, String id) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        metadata.setContentDisposition(STR."inline; filename=\{image.getOriginalFilename()}");

        String path = "ecran" + "/" + "Peliculas";
        String fileName = image.getOriginalFilename();

        try {
            InputStream iStream = image.getInputStream();
            amazonS3.putObject(path, fileName, iStream, metadata);
            amazonS3.setObjectAcl(path, fileName, CannedAccessControlList.PublicRead);
        } catch (AmazonServiceException | IOException e) {
            throw new ServiceException(e.getMessage());
        }

        return STR."https://akhanta.s3.amazonaws.com/\{folder}/\{id}";
    }

}