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

    public Product getProduct(StockKeepingUnit sku) {
        return productMapper.toDomain(repository.findById(sku.asUUID()).get());
    }

    public ProductStockAmount getProductStockAmount(StockKeepingUnit sku) {
        return ProductStockAmount.of(repository.findById(sku.asUUID()).get().getStock());
    }


    public void saveProductStockAmount(StockKeepingUnit sku, ProductStockAmount amount) {
        ProductOrm orm = repository.findById(sku.asUUID()).get();
        orm.setStock(amount.asLong());
        repository.save(orm);
    }

    public ProductPrice getProductPrice(StockKeepingUnit sku) {
        return ProductPrice.of(repository.findById(sku.asUUID()).get().getPrice()); //TODO isPresent check!
    }

    public ProductInventory getProductInventory() {
        List<Product> productList = new ArrayList<>();
        Iterable<ProductOrm> ormIterable = repository.findAll();
        while (ormIterable.iterator().hasNext()) {
            ProductOrm orm = ormIterable.iterator().next();
            productList.add(productMapper.toDomain(orm));
        }

        return ProductInventory.builder().products(productList).build();
    }
}
