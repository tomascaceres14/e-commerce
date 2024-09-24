package com.tomasdev.akhanta.admin;

import com.tomasdev.akhanta.admin.dto.PatchRole;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.orders.ShopOrder;
import com.tomasdev.akhanta.orders.ShopOrderService;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.product.categories.Category;
import com.tomasdev.akhanta.product.categories.CategoryService;
import com.tomasdev.akhanta.users.User;
import com.tomasdev.akhanta.users.UserService;
import com.tomasdev.akhanta.shop.ShopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final ShopService shopService;
    private final ShopOrderService orderService;

    /* -- USERS -- */

    @GetMapping("/customers")
    public ResponseEntity<Page<User>> findAllCustomers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("[ /admin/customers - GET ]");
        return ResponseEntity.ok().body(userService.findAll(page, size));
    }

    @GetMapping("/customers/{email}")
    public ResponseEntity<User> findCustomerByEmail(@PathVariable String email) {
        log.info("[ /admin/customers - GET ]");
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @PatchMapping("/customers/{id}/status")
    public ResponseEntity<Integer> setCustomerStatus(@PathVariable String id) {
        log.info("[ /admin/customers/{id}/status - PATCH ]");
        return ResponseEntity.ok().body(userService.updateStatusById(id, 10));
    }

    @PatchMapping("/customers/{id}/role")
    public ResponseEntity<Integer> updateRoleById(@PathVariable String id, @RequestBody PatchRole roleDTO) {
        log.info("[ /admin/customers/{id}/role - PATCH ]");
        return ResponseEntity.ok().body(userService.updateRoleById(id, roleDTO.getRole()));
    }

    /* --  SHOPS -- */
    @GetMapping("/shops")
    public ResponseEntity<Page<HomeShopDTO>> findAllShops(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("[ /admin/shops - GET ]");
        return ResponseEntity.ok(shopService.findAllShops(page, size));
    }

    @GetMapping("/shops/{seName}")
    public ResponseEntity<HomeShopDTO> findShopBySeName(@PathVariable String seName) {
        log.info("[ /admin/shops/{seName} - GET ]");
        return ResponseEntity.ok(shopService.findBySeName(seName));
    }

    /* -- PRODUCTS -- */

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> filterProducts(@RequestParam(required = false, defaultValue = "") String seTitle,
                                                        @RequestParam(required = false, defaultValue = "") String categoryId,
                                                        @RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("[ /admin/products - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.filterProducts(seTitle, categoryId, page, size));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable String id) {
        log.info("[ /admin/products/{id} - DELETE ]");
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(productService.deleteProductById(id));
    }

    /* -- CATEGORIES -- */

    @PostMapping("/products/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        log.info("[ /admin/products/categories - POST ]");
        categoryService.saveCategory(category);
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

    @PutMapping("/products/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.updateById(id, category));
    }

    @GetMapping("/products/categories/{node}")
    public ResponseEntity<Category> findCategoryByNode(@PathVariable String node) {
        log.info("[ /admin/products/categories/{node} - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryByNode(node));
    }

    @DeleteMapping("/products/categories/{id}")
    public ResponseEntity deleteCategoriesById(@PathVariable String id) {
        log.info("[ /admin/products/categories/{id} - DELETE ]");
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }

    /* -- ORDERS -- */

    @GetMapping("/orders")
    public ResponseEntity<Page<ShopOrder>> findAllOrdersByShop(@RequestParam(required = false, defaultValue = "") String customerId,
                                                               @RequestParam(required = false, defaultValue = "") String shopId,
                                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("[ /admin/orders - GET ]");
        return ResponseEntity.ok(orderService.filterOrders(customerId, shopId, page, size));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String id) {
        log.info("[ /admin/orders/{id} - DELETE ]");
        return ResponseEntity.ok(orderService.deleteOrderById(id));
    }

    /* -- SERVICES -- */

}