package de.inits.io.shoppingcart.product.domain.valueobjects;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class ProductPrice {

    private BigDecimal price;

    private ProductPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal asBigDecimal() {
        return price;
    }

    public static ProductPrice of(BigDecimal price) {
        return new ProductPrice(price);
    }

    public boolean isPriceNonZero() {
        return this.price.signum() > 0;
    }
}
