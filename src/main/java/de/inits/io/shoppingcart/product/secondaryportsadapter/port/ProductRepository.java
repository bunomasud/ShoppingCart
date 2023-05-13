package de.inits.io.shoppingcart.product.secondaryportsadapter.port;

import de.inits.io.shoppingcart.product.domain.aggregateroot.ProductInventory;
import de.inits.io.shoppingcart.product.domain.entity.Product;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import de.inits.io.shoppingcart.product.secondaryportsadapter.adapter.ProductJpaAdapter;
import de.inits.io.shoppingcart.product.secondaryportsadapter.repository.ProductOrm;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductRepository {

    private final IProductRepositoryPort repository;
    private final ProductJpaAdapter productMapper;

    public ProductRepository(IProductRepositoryPort repository, ProductJpaAdapter productMapper) {
        this.repository = repository;
        this.productMapper = productMapper;
    }

    public void saveProduct(Product product) {
        ProductOrm productOrm = productMapper.toOrm(product);
        repository.save(productOrm);
    }

    public void deleteProduct(Product product) {
        ProductOrm productOrm = productMapper.toOrm(product);
        repository.delete(productOrm);
    }

    public Optional<Product> getProduct(StockKeepingUnit sku) {
        if (repository.findById(sku.asUUID()).isPresent()) {
            ProductOrm Orm = repository.findById(sku.asUUID()).get();
            return Optional.of(productMapper.toDomain(Orm));
        }
        return Optional.empty();
    }

    public ProductStockAmount getProductStockAmount(StockKeepingUnit sku) {
        return ProductStockAmount.of(repository.findById(sku.asUUID()).get().getStock()); //TODO isPresnt?
    }


    public void saveProductStockAmount(StockKeepingUnit sku, ProductStockAmount amount) {
        ProductOrm orm = repository.findById(sku.asUUID()).get();//TODO isPresnt?
        orm.setStock(amount.asLong());
        repository.save(orm);
    }

    public ProductPrice getProductPrice(StockKeepingUnit sku) {
        return ProductPrice.of(repository.findById(sku.asUUID()).get().getPrice()); //TODO isPresent check!
    }

    public ProductInventory getProductInventory() {
        List<Product> productList = new ArrayList<>();
        repository.findAll().forEach(productOrm -> productList.add(productMapper.toDomain(productOrm)));
        return ProductInventory.builder().products(productList).build();
    }
}
