package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.Associate;
import org.springframework.web.multipart.MultipartFile;

public interface AssociateService extends iService<Associate> {

    Associate saveWithImages(Associate req, MultipartFile profile, MultipartFile banner);
    Associate updateWithImages(String id, Associate req, MultipartFile profile, MultipartFile banner);
}