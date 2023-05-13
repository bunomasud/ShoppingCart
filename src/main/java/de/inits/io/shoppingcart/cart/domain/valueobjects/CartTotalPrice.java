package de.inits.io.shoppingcart.cart.domain.valueobjects;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class CartTotalPrice {

    private BigDecimal totalPrice;

    private CartTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal asBigDecimal() {
        return totalPrice;
    }

    public static CartTotalPrice of(BigDecimal price) {
        return new CartTotalPrice(price);
    }

    public boolean isPriceNonZero() {
        return this.totalPrice.signum() > 0;
    }
}
