package de.inits.io.shoppingcart.cart.primaryadatpter.port.rest;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponseData {

    private long quantity;
    private String sku;
    private BigDecimal price;
}
