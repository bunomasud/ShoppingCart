package de.inits.io.shoppingcart.cart.domain.entity;

import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCheckedOut;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCurrent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CartStatus {

    private CartStatusCheckedOut checkedOut;
    private CartStatusCurrent cartStatusCurrent;
}
