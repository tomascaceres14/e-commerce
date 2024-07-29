package com.tomasdev.akhanta.shop;

import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.users.UserRepository;
import com.tomasdev.akhanta.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ModelMapper mapper;
    private final ShopRepository repository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public HomeShopDTO findBySeName(String seName) {
        Shop shop = repository.findBySeName(seName).orElseThrow(
                () -> new ResourceNotFoundException("Shop no encontrado."));
        return mapper.map(shop, HomeShopDTO.class);
    }

    @Override
    public Page<HomeShopDTO> findAllShops(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Shop> shopsPage = repository.findAll(pageable);

        List<HomeShopDTO> homeShopDTOS = shopsPage.getContent()
                                            .stream()
                                            .map(shop -> mapper.map(shop, HomeShopDTO.class))
                                            .toList();
        return new PageImpl<>(homeShopDTOS, PageRequest.of(page, 15), shopsPage.getTotalElements());
    }

    @Override
    public Shop saveShop(ShopRegisterDTO shop) {
        Shop newShop = mapper.map(shop, Shop.class);
        newShop.setSeName(StringUtils.normalizeToSearch(newShop.getName()));

        if (!userRepository.existsById(newShop.getOwnerId())) {
            throw new ResourceNotFoundException(STR."Usuario id \{newShop.getOwnerId()} no existe");
        }

        return repository.save(newShop);
    }

    @Override
    public void addProductById(String id, String productId) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().push("products", productId);
        mongoTemplate.updateFirst(query, update, Shop.class);
    }

}
