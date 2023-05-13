package de.inits.io.shoppingcart.cart.domain.entity;

import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemQuantity;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
@Builder
public class CartItem {

    private UUID itemId;
    private CartItemSku sku;
    private CartItemPrice price;
    private CartItemQuantity quantity;
    private CartItemStockAmount stockAmount;
}
