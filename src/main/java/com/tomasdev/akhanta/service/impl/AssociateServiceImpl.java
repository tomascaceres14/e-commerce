package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.repository.AssociateRepository;
import com.tomasdev.akhanta.service.AssociateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository repository;
    private final AmazonS3ServiceImpl s3Service;
    private final ModelMapper mapper;

    @Override
    public Associate save(Associate req) {
        return repository.save(req);
    }

    @Override
    public Associate saveWithImages(Associate req, MultipartFile profile, MultipartFile banner) {
        req.setProfile_url(s3Service.upload(profile, "asociados"));
        req.setBanner_url(s3Service.upload(banner, "asociados"));
        return repository.save(req);
    }

    @Override
    public Page<Associate> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Associate findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Associate id \{id} doesn't exists"));
    }

    @Override
    public Associate updateById(String id, Associate req) {
        Associate associateDB = findById(id);
        mapper.map(req, associateDB);
        associateDB.setId(id);
        return repository.save(associateDB);
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(STR."Associate id \{id} doesn't exists");
        }
        repository.deleteById(id);
    }
}
