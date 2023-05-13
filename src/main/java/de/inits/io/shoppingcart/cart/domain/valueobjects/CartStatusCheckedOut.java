package de.inits.io.shoppingcart.cart.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class CartStatusCheckedOut {

    private boolean isCheckedOut;

    public CartStatusCheckedOut(boolean isCheckedOut) {
        this.isCheckedOut = isCheckedOut;
    }

    public boolean asBoolean() {
        return this.isCheckedOut;
    }

    public static CartStatusCheckedOut of(boolean isCheckedOut) {
        return new CartStatusCheckedOut(isCheckedOut);
    }

}
