package com.tomasdev.akhanta.admin;


import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.orders.ShopOrder;
import com.tomasdev.akhanta.orders.ShopOrderService;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.product.categories.Category;
import com.tomasdev.akhanta.product.categories.CategoryService;
import com.tomasdev.akhanta.users.customer.Customer;
import com.tomasdev.akhanta.users.customer.CustomerService;
import com.tomasdev.akhanta.users.shop.ShopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private CategoryService categoryService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ShopService shopService;
    private final ShopOrderService orderService;

    /* -- CUSTOMERS -- */

    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> findAllCustomers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(customerService.findAll(page, size));
    }

    // TODO El objetivo no es eliminarlos, sino cambiarlos a un estado de inhabilitado. De momento queda asi hasta que se implementen estados.
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String id) {
        log.info("[ /admin/articles/id - DELETE ]");
        customerService.deleteById(id);
        return ResponseEntity.ok(STR."Cliente id \{id} eliminado.");
    }

    /* --  SHOPS -- */

    @GetMapping("/shops")
    public ResponseEntity<Page<HomeShopDTO>> findAllShops(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(shopService.findAllShops(page, size));
    }

    @GetMapping("/shops/{seName}")
    public ResponseEntity<HomeShopDTO> findShopBySeName(@PathVariable String seName) {
        return ResponseEntity.ok(shopService.findBySeName(seName));
    }

    /* -- PRODUCTS -- */

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllProducts(page, size));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Category> findProductById(@RequestParam String id) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryById(id));
    }

    /* -- CATEGORIES -- */

    @PostMapping("/products/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category categor) {
        log.info("[ /admin/products/categories - POST ]");
        categoryService.saveCategory(categor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findAllCategories());
    }

    @GetMapping("/products/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable String id) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryById(id));
    }

    /* -- ORDERS -- */
    @GetMapping("/shops/orders")
    public ResponseEntity<Page<ShopOrder>> findAllOrdersByShop(@RequestParam(required = false, defaultValue = "") String customerId,
                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(orderService.findAllOrdersByShop(jwt, customerId, page));
    }

    @GetMapping("/customers/orders")
    public ResponseEntity<Page<ShopOrder>> findAllOrdersByCustomer(@RequestParam(required = false, defaultValue = "") String shopId,
                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(orderService.findAllOrdersByCustomer(jwt, shopId, page));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String id) {
        log.info("[ /admin/orders/id - DELETE ]");
        return ResponseEntity.ok(orderService.deleteOrderById(id));
    }


    /* -- SERVICES -- */

}