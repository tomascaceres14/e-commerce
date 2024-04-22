package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.model.dto.AssociateRequestDTO;
import com.tomasdev.akhanta.repository.AssociateRepository;
import com.tomasdev.akhanta.service.AssociateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository repository;
    private final AmazonS3ServiceImpl s3Service;
    private final ModelMapper mapper;
    private final String s3Folder = "asociados";

    @Override
    public Associate save(Associate req) {
        return repository.save(req);
    }

    @Override
    public Associate saveWithImages(AssociateRequestDTO associateDTO, MultipartFile profile, MultipartFile banner) {
        Associate associate = mapper.map(associateDTO, Associate.class);

        associate.setProfile_url(s3Service.upload(profile, s3Folder));
        associate.setBanner_url(s3Service.upload(banner, s3Folder));

        log.info("[ Creating new associate it a{} ]", new Date());
        return repository.save(associate);
    }

    @Override
    public Associate updateWithImages(String id, AssociateRequestDTO associateDTO, MultipartFile profile, MultipartFile banner) {
        Associate associateDB = findById(id);

        if (profile != null) {
            String imageName =  s3Service.getImageKeyFromUrl(associateDB.getProfile_url());
            associateDB.setProfile_url(s3Service.update(profile, s3Folder, imageName));
        }

        if (banner != null) {
            String imageName =  s3Service.getImageKeyFromUrl(associateDB.getBanner_url());
            associateDB.setBanner_url(s3Service.update(banner, s3Folder, imageName));
        }

        if (associateDTO != null) {
            mapper.map(associateDTO, associateDB);
            associateDB.setAssociateId(id);
        }

        log.info("[ Updating article id: {} - {} ]", id, new Date());
        return repository.save(associateDB);
    }

    @Override
    public Page<Associate> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Associate findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asociado"));
    }

    @Override
    public Associate updateById(String id, Associate req) {
        Associate associateDB = findById(id);
        mapper.map(req, associateDB);
        associateDB.setAssociateId(id);
        return repository.save(associateDB);
    }

    @Override
    public void deleteById(String id) {
        Associate associate = findById(id);
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(associate.getBanner_url()));
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(associate.getProfile_url()));

        log.info("[ Deleting associate id: {} - {} ]", id, new Date());
        repository.deleteById(id);
    }
}
