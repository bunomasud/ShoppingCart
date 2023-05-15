package de.inits.io.shoppingcart.cart.primaryadatpter.adapter;

import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemQuantity;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.primaryadatpter.port.rest.CartItemRequestData;
import de.inits.io.shoppingcart.cart.primaryadatpter.port.rest.CartItemResponseData;
import de.inits.io.shoppingcart.cart.primaryadatpter.port.rest.CartResponseData;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartRestControllerAdapter {

    public CartResponseData adaptToCartResponse(Cart cart) {
        return CartResponseData.builder()
                .cartId(cart.getCartID().toString())
                .items(adaptToCartItemsData(cart.getCartItems()))
                .totalPrice(cart.getTotalPrice().asBigDecimal()).build();
    }

    private List<CartItemResponseData> adaptToCartItemsData(List<CartItem> cartItems) {
        return cartItems.stream().map(this::adaptToCartItemData).collect(Collectors.toList());
    }

    private CartItemResponseData adaptToCartItemData(CartItem cartItem) {
        return CartItemResponseData.builder()
                .price(cartItem.getPrice().asBigDecimal())
                .sku(cartItem.getSku().asString())
                .quantity(cartItem.getQuantity().asLong()).build();
    }

    public List<CartItem> adaptToCartItems(List<CartItemRequestData> cartItemRequestData) {
        return cartItemRequestData.stream().map(this::adaptToCartItem).collect(
                Collectors.toList());
    }

    private CartItem adaptToCartItem(CartItemRequestData cartItemRequestData) {
        return CartItem.builder()
                .sku(CartItemSku.of(cartItemRequestData.getSku()))
                .quantity(CartItemQuantity.of(cartItemRequestData.getQuantity())).build();
    }


}
