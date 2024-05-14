package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.model.Product;
import com.tomasdev.akhanta.model.dto.ProductRequestDTO;
import com.tomasdev.akhanta.repository.ProductRepository;
import com.tomasdev.akhanta.service.AmazonS3Service;
import com.tomasdev.akhanta.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;
    private final AmazonS3Service s3Service;
    private final ProductRepository repository;
    private final MongoTemplate template;

    @Override
    public Page<Product> findAllProducts(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Product saveProduct(ProductRequestDTO productDTO, List<MultipartFile> images) {
        Product product = mapper.map(productDTO, Product.class);
        List<String> imagesUrl = new ArrayList<>();

        if (images != null) {
            if (images.size() > 5) throw new ServiceException("Se ha excedido el máximo de 5 imágenes por producto");

            images.forEach(image -> {
                imagesUrl.add(s3Service.upload(image, "productos"));
            });
            product.setImages(imagesUrl);
        }

        return repository.save(product);
    }

    @Override
    public Product findProductById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Producto id \{id} no encontrado"));
    }

    @Override
    public Product updateProductById(String id, ProductRequestDTO product) {
        Product productDB = findProductById(id);

        mapper.map(product, productDB);

       return repository.save(productDB);
    }

    @Override
    public String deleteProductById(String id) {
        Product product = mapper.map(findProductById(id), Product.class);

        product.getImages().forEach(image -> {
            s3Service.delete("productos", s3Service.getImageKeyFromUrl(image));
        });

        repository.deleteById(id);
        return STR."Producto id \{id} eliminado.";
    }

    @Override
    public Page<Product> filterProducts(String name, int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAllProductsByName(name, pageable);
    }

}
