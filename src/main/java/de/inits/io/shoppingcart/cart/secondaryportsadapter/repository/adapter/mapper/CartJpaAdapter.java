package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.adapter.mapper;

import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.entity.CartStatus;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemQuantity;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCheckedOut;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCurrent;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartTotalPrice;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartItemOrm;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartOrm;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CartJpaAdapter {

    public CartOrm toOrm(Cart cart) {
        if (cart.getCartID() != null) {
            return CartOrm.builder().id(cart.getCartID())
                    .current(cart.getCartStatus().getCartStatusCurrent().asBoolean())
                    .checkedOut(cart.getCartStatus().getCheckedOut().asBoolean())
                    .items(cart.getCartItems().stream().map(this::adaptToCartItemOrm).collect(Collectors.toSet()))
                    .build();
        }
        return CartOrm.builder()
                .current(cart.getCartStatus().getCartStatusCurrent().asBoolean())
                .checkedOut(cart.getCartStatus().getCheckedOut().asBoolean())
                .items(cart.getCartItems().stream().map(this::adaptToCartItemOrm).collect(Collectors.toSet()))
                .build();
    }

    private CartItemOrm adaptToCartItemOrm(CartItem item) {
        if (item.getItemId() != null) {
            return CartItemOrm.builder().id(item.getItemId())
                    .sku(item.getSku().asString())
                    .id(item.getItemId())
                    .price(item.getPrice().asBigDecimal())
                    .quantity(item.getQuantity().asLong()).build();
        }
        return CartItemOrm.builder()
                .sku(item.getSku().asString())
                .price(item.getPrice().asBigDecimal())
                .quantity(item.getQuantity().asLong()).build();
    }

    public Cart toDomain(CartOrm cartOrm) {
        CartStatus cartStatus = CartStatus.builder()
                .cartStatusCurrent(CartStatusCurrent.of(cartOrm.isCurrent()))
                .checkedOut(CartStatusCheckedOut.of(cartOrm.isCheckedOut())).build();
        Cart cart = new Cart();
        cart.setCartID(cartOrm.getId());
        cart.setCartStatus(cartStatus);
        cart.setCartItems(cartOrm.getItems().stream().map(this::adaptToCartItemDomain)
                .collect(Collectors.toList()));
        cart.setTotalPrice(CartTotalPrice.of(calculateTotalPrice(cartOrm.getItems())));
        return cart;

    }

    private BigDecimal calculateTotalPrice(Set<CartItemOrm> items) {
        return items.stream().map(itemOrm -> itemOrm.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CartItem adaptToCartItemDomain(CartItemOrm itemOrm) {
        return CartItem.builder()
                .sku(CartItemSku.of(itemOrm.getSku()))
                .itemId(itemOrm.getId())
                .price(CartItemPrice.of(itemOrm.getPrice()))
                .quantity(CartItemQuantity.of(itemOrm.getQuantity())).build();
    }
}
