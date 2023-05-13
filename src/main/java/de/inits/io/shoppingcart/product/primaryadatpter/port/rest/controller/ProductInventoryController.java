package de.inits.io.shoppingcart.product.primaryadatpter.port.rest.controller;

import de.inits.io.shoppingcart.product.applicationservice.service.ProductApplicationService;
import de.inits.io.shoppingcart.product.domain.aggregateroot.ProductInventory;
import de.inits.io.shoppingcart.product.domain.entity.Product;
import de.inits.io.shoppingcart.product.primaryadatpter.port.adapter.ProductRestControllerAdapter;
import de.inits.io.shoppingcart.product.primaryadatpter.port.rest.InventoryData;
import de.inits.io.shoppingcart.product.primaryadatpter.port.rest.ProductData;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class ProductInventoryController {

    private final ProductRestControllerAdapter adapter;
    private final ProductApplicationService service;

    public ProductInventoryController(ProductRestControllerAdapter adapter, ProductApplicationService service) {
        this.adapter = adapter;
        this.service = service;
    }

    @GetMapping("/products")
    public InventoryData getInventory() {
        ProductInventory productInventory = service.getProducts();
        return adapter.adaptToInventoryData(productInventory);
    }

    @PostMapping("/product")
    public void addProduct(@RequestBody ProductData productData) {
        service.addProduct(adapter.adaptToAddProductCommand(productData));
    }

    @GetMapping("/product/{sku}/")
    public ProductData getProduct(@PathVariable String sku) {
        Optional<Product> productOptional = service.getProduct(adapter.adaptToGetStockQuery(UUID.fromString(sku)));
        if (productOptional.isPresent()) {
            return adapter.adaptToProductData(productOptional.get());
        }
        return ProductData.builder().build();
    }

    @PatchMapping("/product")
    public void editProduct(@RequestBody ProductData productData) {
        service.editProduct(adapter.adaptToEditProductCommand((productData)));
    }

    @DeleteMapping("/product/{sku}/")
    public void deleteProduct(@PathVariable String sku) {
        service.deleteProduct(adapter.adaptToRemoveProductCommand((UUID.fromString(sku))));
    }
}
