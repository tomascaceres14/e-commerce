package com.tomasdev.akhanta.orders;

import com.tomasdev.akhanta.cart.Cart;
import com.tomasdev.akhanta.cart.CartItem;
import com.tomasdev.akhanta.cart.CartService;
import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.user.User;
import com.tomasdev.akhanta.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopOrderServiceImpl implements ShopOrderService {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService customerService;
    private final ShopOrderRepository repository;

    public Map<String, List<CartItem>> groupItemsByShopId(List<CartItem> items) {
        System.out.println(items);
        return items.stream().collect(Collectors.groupingBy(CartItem::getShopId));
    }

    public Double totalPricePerShop(Map<String, List<CartItem>> items, String shopId) {

        Double totalPrice = 0.0;

        for (Map.Entry<String, List<CartItem>> entry : items.entrySet()) {
            String key = entry.getKey();
            List<CartItem> itemsList = entry.getValue();

            if (key.equals(shopId)) {
                for (CartItem cartItem : itemsList) {
                    totalPrice += cartItem.getItemPrice() * cartItem.getQuantity();
                }
            }
        }

        return totalPrice;
    }

    @Override
    public List<ShopOrder> createOrder(String jwt) {
        Cart cart = cartService.findCartById(jwt);

        if (cart.getItems().isEmpty()) {
            throw new ServiceException("El carrito está vacío.");
        }

        User user = customerService.findById(JwtService.extractClaim(jwt, "customerId"));
        System.out.println(cart);
        Map<String, List<CartItem>> itemsByShop = groupItemsByShopId(cart.getItems());

        List<ShopOrder> orders = new ArrayList<>();
        itemsByShop.forEach((k, v) -> {
            Double totalPrice = totalPricePerShop(itemsByShop, k);
            ShopOrder order = new ShopOrder(
                            cart.getCustomerId(),
                            k, v,
                            totalPrice,
                            user.getAddress(),
                            "CASH",
                            "REGULAR");

            v.forEach( i -> {
                productService.removeStockById(i.getProductId(), i.getQuantity());
            });

            repository.save(order);
            orders.add(order);
        });

        cartService.clearCart(jwt);
        return orders;
    }

    @Override
    public Page<ShopOrder> filterOrders(String customerId, String shopId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findAllFiltered(customerId, shopId, pageable);
    }

    @Override
    public Page<ShopOrder> findAllOrdersByShop(String jwt, String customerId, Integer page) {
        PageRequest pageable = PageRequest.of(page, 10);
        String shopId = JwtService.extractClaim(jwt, "shopId");
        return repository.findAllFiltered(customerId, shopId, pageable);
    }

    @Override
    public Page<ShopOrder> findAllOrdersByCustomer(String jwt, String shopId, Integer page) {
        PageRequest pageable = PageRequest.of(page, 10);
        String customerId = JwtService.extractClaim(jwt, "customerId");

        return repository.findAllFiltered(customerId, shopId, pageable);
    }

    @Override
    public String deleteOrderById(String id) {
        repository.deleteById(id);
        return STR."Orden id \{id} eliminada";
    }
}
