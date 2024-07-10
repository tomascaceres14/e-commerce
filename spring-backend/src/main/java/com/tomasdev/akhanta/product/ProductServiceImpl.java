package com.tomasdev.akhanta.product;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.images.AmazonS3Service;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.users.shop.ShopService;
import com.tomasdev.akhanta.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;
    private final AmazonS3Service s3Service;
    private final ShopService shopService;
    private final ProductRepository repository;

    @Override
    public Page<Product> findAllProducts(int page) {
        return findAllProducts(page, 10);
    }

    @Override
    public Page<Product> findAllProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Product saveProduct(CreateProductDTO productDTO, List<MultipartFile> images, String jwt) {
        Product product = mapper.map(productDTO, Product.class);
        List<String> imagesUrl = new ArrayList<>();

        if (images != null) {
            if (images.size() > 5) throw new ServiceException("Se ha excedido el máximo de 5 imágenes por producto");

            images.forEach(image -> {
                imagesUrl.add(s3Service.upload(image, "productos"));
            });
            product.setImages(imagesUrl);
        }

        product.setSeTitle(StringUtils.normalizeToSearch(product.getTitle()));
        product.setStatus(1);
        product.setShopId(JwtService.extractClaim(jwt, "shopId"));
        Product savedProduct = repository.save(product);

        shopService.addProductById(product.getShopId(), savedProduct.getProductId());

        return savedProduct;
    }

    @Override
    public Product findProductById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(STR."Producto id \{id} no encontrado"));
    }

    @Override
    public Product updateProductById(String id, CreateProductDTO product) {
        Product productDB = findProductById(id);

        mapper.map(product, productDB);

       return repository.save(productDB);
    }

    @Override
    public String deleteProductById(String id) {
        Product product = mapper.map(findProductById(id), Product.class);
        List<String> images = product.getImages();

        if (images != null) {
            product.getImages().forEach(image -> {
                s3Service.delete("productos", s3Service.getImageKeyFromUrl(image));
            });
        }

        repository.deleteById(id);
        return STR."Producto id \{id} eliminado.";
    }

    @Override
    public void removeStockById(String productId, Integer quantity) {
        Product product = findProductById(productId);
        product.setStock(product.getStock() - quantity);
        repository.save(product);
    }

    @Override
    public Page<Product> filterProducts(String name, String categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.filterAllProducts(name, categoryId, pageable);
    }

}
