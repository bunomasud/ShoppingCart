package de.inits.io.shoppingcart.cart.primaryadatpter.port.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestData {

    private long quantity;
    private String sku;
}
