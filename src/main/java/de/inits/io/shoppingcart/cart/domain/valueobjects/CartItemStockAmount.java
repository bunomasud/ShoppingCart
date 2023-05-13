package de.inits.io.shoppingcart.cart.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class CartItemStockAmount {

    private Long amount;

    private CartItemStockAmount(Long amount) {
        if (amount >= 0l) {
            this.amount = amount;
        } else {
            this.amount = 0l;
        }
    }

    public Long asLong() {
        return amount;
    }

    public static CartItemStockAmount of(Long amount) {
        return new CartItemStockAmount(amount);
    }

    public boolean isMoreThanZero() {
        return amount > 0;
    }
}
