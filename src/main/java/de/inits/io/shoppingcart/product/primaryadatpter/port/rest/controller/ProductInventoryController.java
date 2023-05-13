package de.inits.io.shoppingcart.product.primaryadatpter.port.rest.controller;

import de.inits.io.shoppingcart.product.applicationservice.service.ProductApplicationService;
import de.inits.io.shoppingcart.product.domain.aggregateroot.ProductInventory;
import de.inits.io.shoppingcart.product.primaryadatpter.port.adapter.ProductRestControllerAdapter;
import de.inits.io.shoppingcart.product.primaryadatpter.port.rest.InventoryData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductInventoryController {

    private final ProductRestControllerAdapter adapter;
    private final ProductApplicationService service;

    public ProductInventoryController(ProductRestControllerAdapter adapter, ProductApplicationService service) {
        this.adapter = adapter;
        this.service = service;
    }

    @GetMapping("/inventory")
    public InventoryData getInventory() {
        ProductInventory productInventory = service.getProducts();
        return adapter.adaptToInventoryData(productInventory);
    }

    //TODO implement other APIs
}
