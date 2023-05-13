package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.adapter.mapper;

import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.entity.CartStatus;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemQuantity;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCheckedOut;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCurrent;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartItemOrm;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartOrm;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CartJpaAdapter {

    public CartOrm toOrm(Cart cart) {
        return CartOrm.builder()
                .id(cart.getCartID())
                .current(cart.getCartStatus().getCartStatusCurrent().asBoolean())
                .checkedOut(cart.getCartStatus().getCheckedOut().asBoolean())
                .items(cart.getCartItems().stream().map(this::adaptToCartItemOrm).collect(Collectors.toSet()))
                .build();
    }

    private CartItemOrm adaptToCartItemOrm(CartItem item) {
        return CartItemOrm.builder()
                .sku(item.getSku().asString())
                .id(item.getItemId())
                .price(item.getPrice().asBigDecimal())
                .quantity(item.getQuantity().asLong()).build();
    }

    public Cart toDomain(CartOrm cartOrm) {
        CartStatus cartStatus = CartStatus.builder()
                .cartStatusCurrent(CartStatusCurrent.of(cartOrm.isCurrent()))
                .checkedOut(CartStatusCheckedOut.of(cartOrm.isCheckedOut())).build();
        return Cart.builder()
                .cartID(cartOrm.getId())
                .cartStatus(cartStatus)
                .cartItems(cartOrm.getItems().stream().map(this::adaptToCartItemDomain)
                        .collect(Collectors.toList())).build();

    }

    private CartItem adaptToCartItemDomain(CartItemOrm itemOrm) {
        return CartItem.builder()
                .sku(CartItemSku.of(itemOrm.getSku()))
                .itemId(itemOrm.getId())
                .price(CartItemPrice.of(itemOrm.getPrice()))
                .quantity(CartItemQuantity.of(itemOrm.getQuantity())).build();
    }
}
