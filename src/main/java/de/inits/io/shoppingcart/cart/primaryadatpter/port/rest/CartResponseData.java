package de.inits.io.shoppingcart.cart.primaryadatpter.port.rest;

import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponseData {

    private String cartId;
    private BigDecimal totalPrice;
    private List<CartItem> items;
}
