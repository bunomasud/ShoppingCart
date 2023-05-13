package de.inits.io.shoppingcart.product.applicationservice.service;

import de.inits.io.shoppingcart.product.applicationservice.commands.AddProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.EditProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.RemoveProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.setProductStockCommand;
import de.inits.io.shoppingcart.product.applicationservice.querys.ProductPricingQuery;
import de.inits.io.shoppingcart.product.applicationservice.querys.ProductStockQuery;
import de.inits.io.shoppingcart.product.domain.aggregateroot.ProductInventory;
import de.inits.io.shoppingcart.product.domain.entity.Product;
import de.inits.io.shoppingcart.product.domain.valueobjects.EuropeanArticleNumber;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.secondaryportsadapter.port.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductApplicationService {

    private final ProductRepository repositoryAdapter;

    public ProductApplicationService(ProductRepository repositoryAdapter) {
        this.repositoryAdapter = repositoryAdapter;
    }

    public void addProduct(AddProductCommand addProductCommand) {
        List<EuropeanArticleNumber> eans = new ArrayList<>();
        eans.add(addProductCommand.europeanArticleNumber());
        Product product = Product.builder()
                .name(addProductCommand.name())
                .sku(addProductCommand.sku())
                .stockAmount(addProductCommand.stockAmount())
                .price(addProductCommand.price())
                .europeanArticleNumberList(eans).build();
        repositoryAdapter.saveProduct(product);
    }

    public void editProduct(EditProductCommand editProductCommand) {
        List<EuropeanArticleNumber> eans = new ArrayList<>();
        eans.add(editProductCommand.europeanArticleNumber());
        Product product = repositoryAdapter.getProduct(editProductCommand.sku());
        Product editedProduct = Product.builder()
                .name(editProductCommand.name())
                .sku(editProductCommand.sku())
                .stockAmount(editProductCommand.stockAmount())
                .price(editProductCommand.price())
                .europeanArticleNumberList(eans).build();
        repositoryAdapter.deleteProduct(product);
        repositoryAdapter.saveProduct(editedProduct);
    }

    public void deleteProduct(RemoveProductCommand removeProductCommand) {
        Product product = repositoryAdapter.getProduct(removeProductCommand.sku());
        repositoryAdapter.deleteProduct(product);
    }

    public ProductPrice getProductPrice(ProductPricingQuery productPricingQuery) {
        return repositoryAdapter.getProductPrice(productPricingQuery.sku());
    }

    public ProductStockAmount getProductStock(ProductStockQuery productStockQuery) {
        return repositoryAdapter.getProductStockAmount(productStockQuery.sku());
    }

    public void setProductStock(setProductStockCommand setProductStockCommand) {
        repositoryAdapter.saveProductStockAmount(setProductStockCommand.sku(), setProductStockCommand.stockAmount());
    }

    public ProductInventory getProducts() {
        return repositoryAdapter.getProductInventory();
    }

}
