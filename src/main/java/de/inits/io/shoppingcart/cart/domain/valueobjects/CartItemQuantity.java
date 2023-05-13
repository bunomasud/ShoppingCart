package de.inits.io.shoppingcart.cart.domain.valueobjects;

public class CartItemQuantity {

    private final Long quantity;

    private CartItemQuantity(Long amount) {
        if (amount >= 0L) {
            this.quantity = amount;
        } else {
            this.quantity = 0L;
        }
    }

    public Long asLong() {
        return quantity;
    }

    public static CartItemQuantity of(Long amount) {
        return new CartItemQuantity(amount);
    }

    public boolean isMoreThanZero() {
        return quantity > 0L;
    }
}
