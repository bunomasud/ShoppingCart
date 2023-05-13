package de.inits.io.shoppingcart.cart.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class CartStatusCurrent {

    private boolean isStatusCurrent;

    public CartStatusCurrent(boolean isStatusCurrent) {
        this.isStatusCurrent = isStatusCurrent;
    }

    public boolean asBoolean() {
        return isStatusCurrent;
    }

    public static CartStatusCurrent of(boolean isStatusCurrent) {
        return new CartStatusCurrent(isStatusCurrent);
    }
}
