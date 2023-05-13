package de.inits.io.shoppingcart.product.domain.aggregateroot;

import de.inits.io.shoppingcart.product.domain.entity.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter(AccessLevel.NONE)
@Builder
public class ProductInventory {

    private List<Product> products;

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Optional<Product> findProductInInventory(Product product) {
        return products.stream().filter(product1 -> product1.getSku().equals(product.getSku())).findAny();
    }
}
