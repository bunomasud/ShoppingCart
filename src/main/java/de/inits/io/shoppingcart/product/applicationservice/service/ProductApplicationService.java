package de.inits.io.shoppingcart.product.applicationservice.service;

import de.inits.io.shoppingcart.product.applicationservice.commands.AddProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.EditProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.RemoveProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.setProductStockCommand;
import de.inits.io.shoppingcart.product.applicationservice.querys.GetProductQuery;
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
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductApplicationService {

    private final ProductRepository productRepository;

    public ProductApplicationService(ProductRepository repositoryAdapter) {
        this.productRepository = repositoryAdapter;
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
        if (product.isProductValid()) {
            productRepository.saveProduct(product);
        }

    }

    public void editProduct(EditProductCommand editProductCommand) {
        List<EuropeanArticleNumber> eans = new ArrayList<>();
        eans.add(editProductCommand.europeanArticleNumber());
        Product editedProduct = Product.builder()
                .name(editProductCommand.name())
                .sku(editProductCommand.sku())
                .stockAmount(editProductCommand.stockAmount())
                .price(editProductCommand.price())
                .europeanArticleNumberList(eans).build();
        if (editedProduct.isProductValid()) {
            Optional<Product> productOptional = productRepository.getProduct(editProductCommand.sku());
            if (productOptional.isPresent()) {
                productRepository.saveProduct(editedProduct);
            }

        }
    }

    public void deleteProduct(RemoveProductCommand removeProductCommand) {
        Optional<Product> productOptional = productRepository.getProduct(removeProductCommand.sku());
        productOptional.ifPresent(productRepository::deleteProduct);

    }

    public Optional<Product> getProduct(GetProductQuery query) {
        return productRepository.getProduct(query.sku());
    }

    public ProductPrice getProductPrice(ProductPricingQuery productPricingQuery) {
        return productRepository.getProductPrice(productPricingQuery.sku());
    }

    public ProductStockAmount getProductStock(ProductStockQuery productStockQuery) {
        return productRepository.getProductStockAmount(productStockQuery.sku());
    }

    public void setProductStock(setProductStockCommand setProductStockCommand) {
        productRepository.saveProductStockAmount(setProductStockCommand.sku(), setProductStockCommand.stockAmount());
    }

    public ProductInventory getProducts() {
        return productRepository.getProductInventory();
    }

}
