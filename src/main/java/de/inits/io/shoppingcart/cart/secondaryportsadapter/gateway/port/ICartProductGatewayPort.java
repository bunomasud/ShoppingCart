package de.inits.io.shoppingcart.cart.secondaryportsadapter.gateway.port;

import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemQuantity;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;

public interface ICartProductGatewayPort {

    CartItemPrice getPrice(CartItemSku sku);

    CartItemStockAmount getStock(CartItemSku sku);

    void setStock(CartItemSku sku, CartItemStockAmount amount);

}
