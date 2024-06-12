package com.tomasdev.akhanta.users.shop;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.exceptions.UserExistsException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.security.Roles;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ModelMapper mapper;
    private final ShopRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;

    @Override
    public Shop register(ShopRegisterDTO shopDTO) {

        if (!shopDTO.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new WrongCredentialsException("Ingrese una dirección de correo válida");
        }

        if (repository.existByEmail(shopDTO.getEmail())) {
            throw new UserExistsException();
        }

        Shop shop = mapper.map(shopDTO, Shop.class);

        shop.setPassword(passwordEncoder.encode(shop.getPassword()));
        shop.setStatus(1);
        shop.setRole(Roles.SHOP);
        shop.setSeName(StringUtils.normalizeToSearch(shop.getName()));

        log.info("[ Registering shop email: {} ]", shopDTO.getEmail());
        return repository.save(shop);
    }

    @Override
    public Shop findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(STR."Shop \{email} no existe."));
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordDTO, String jwt) {

        String email = JwtService.extractClaim(jwt, "email");
        Shop shop = findByEmail(email);

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), shop.getPassword())) {
             throw new WrongCredentialsException("Contraseña incorrecta.");
        }

        if (!passwordDTO.getNewPassword().matches(passwordDTO.getRepeatedPassword())) {
            throw new WrongCredentialsException("Las contraseñas no coinciden");
        }

        shop.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));

        repository.save(shop);
    }

    @Override
    public HomeShopDTO findBySeName(String seName) {
        Shop shop = repository.findBySeName(seName).orElseThrow(
                () -> new ResourceNotFoundException("Shop no encontrado."));
        return mapper.map(shop, HomeShopDTO.class);
    }

    @Override
    public HomeShopDTO findAllShops(int page) {
        PageRequest pageable = PageRequest.of(page, 15);
        return mapper.map(repository.findAll(pageable), HomeShopDTO.class);
    }

    @Override
    public void addProductById(String id, String productId) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().push("products", productId);
        mongoTemplate.updateFirst(query, update, Shop.class);
    }

}
