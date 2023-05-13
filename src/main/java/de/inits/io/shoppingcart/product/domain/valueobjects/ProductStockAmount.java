package de.inits.io.shoppingcart.product.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class ProductStockAmount {

    private Long amount;

    private ProductStockAmount(Long amount) {
        if (amount >= 0l) {
            this.amount = amount;
        } else {
            this.amount = 0l;
        }
    }

    public Long asLong() {
        return amount;
    }

    public static ProductStockAmount of(Long amount) {
        return new ProductStockAmount(amount);
    }

    public boolean isStockAvailable() {
        return amount > 0;
    }
}
