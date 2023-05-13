package de.inits.io.shoppingcart.cart.primaryadatpter.port.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemData {

    private long quantity;
    private String sku;
}
