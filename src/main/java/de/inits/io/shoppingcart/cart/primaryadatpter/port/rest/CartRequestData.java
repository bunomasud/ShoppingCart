package de.inits.io.shoppingcart.cart.primaryadatpter.port.rest;

import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartRequestData {

    private List<CartItem> items;
}
