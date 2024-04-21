package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.model.dto.AssociateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AssociateService extends iService<Associate> {

    Associate saveWithImages(AssociateRequestDTO associateDTO, MultipartFile profile, MultipartFile banner);
    Associate updateWithImages(String id, AssociateRequestDTO associateDTO, MultipartFile profile, MultipartFile banner);
}