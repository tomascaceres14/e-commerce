package com.tomasdev.akhanta.associate;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface AssociateService {

    Page<Associate> findAllAssociates(int page);
    Associate saveAssociate(AssociateRequestDTO associateDTO, MultipartFile profile, MultipartFile banner);
    Associate findAssociateById(String id);
    Associate updateAssociateById(String id, AssociateRequestDTO associateDTO, MultipartFile profile, MultipartFile banner);
    void deleteAssociateById(String id);

}