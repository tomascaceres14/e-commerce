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
    private final String s3Folder = "asociados";

    @Override
    public Associate save(Associate req) {
        return repository.save(req);
    }

    @Override
    public Associate saveWithImages(Associate req, MultipartFile profile, MultipartFile banner) {
        req.setProfile_url(s3Service.upload(profile, s3Folder));
        req.setBanner_url(s3Service.upload(banner, s3Folder));
        return repository.save(req);
    }

    @Override
    public Associate updateWithImages(String id, Associate req, MultipartFile profile, MultipartFile banner) {
        Associate associateDB = findById(id);

        String profileName =  s3Service.getImageKeyFromUrl(associateDB.getProfile_url());
        String bannerName = s3Service.getImageKeyFromUrl(associateDB.getBanner_url());
        req.setProfile_url(s3Service.update(profile, s3Folder, profileName));
        req.setBanner_url(s3Service.update(banner, s3Folder, bannerName));

        mapper.map(req, associateDB);
        associateDB.setId(id);
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
        associateDB.setId(id);
        return repository.save(associateDB);
    }

    @Override
    public void deleteById(String id) {
        Associate associate = findById(id);
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(associate.getBanner_url()));
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(associate.getProfile_url()));
        repository.deleteById(id);
    }
}
