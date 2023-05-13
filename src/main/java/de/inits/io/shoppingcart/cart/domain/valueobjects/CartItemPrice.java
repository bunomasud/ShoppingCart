package de.inits.io.shoppingcart.cart.domain.valueobjects;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class CartItemPrice {

    private BigDecimal price;

    private CartItemPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal asBigDecimal() {
        return price;
    }

    public static CartItemPrice of(BigDecimal price) {
        return new CartItemPrice(price);
    }

    public boolean isPriceNonZero() {
        return this.price.signum() > 0;
    }
}
