package de.inits.io.shoppingcart.cart.domain;

import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.entity.CartStatus;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemQuantity;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCheckedOut;
import java.math.BigDecimal;;
import org.junit.Assert;
import org.junit.Test;

public class CartAggregationTest {

    @Test
    public void addItemTest() {
        Cart cart = new Cart();
        CartItem item1 = CartItem.builder()
                .sku(CartItemSku.of("sku1"))
                .price(CartItemPrice.of(BigDecimal.valueOf(10)))
                .quantity(CartItemQuantity.of(10l))
                .stockAmount(CartItemStockAmount.of(11l)).build();

        CartItem item2 = CartItem.builder()
                .sku(CartItemSku.of("sku2"))
                .price(CartItemPrice.of(BigDecimal.valueOf(10)))
                .quantity(CartItemQuantity.of(10l))
                .stockAmount(CartItemStockAmount.of(1l)).build();
        Assert.assertTrue(cart.addItem(item1));
        Assert.assertFalse(cart.addItem(item2));
        Assert.assertEquals(BigDecimal.valueOf(10), cart.getTotalPrice().asBigDecimal());

    }

    @Test
    public void editItemTest() {

        Cart cart = new Cart();
        cart.setCartStatus(CartStatus.builder().checkedOut(CartStatusCheckedOut.of(false)).build());
        CartItem item1 = CartItem.builder()
                .sku(CartItemSku.of("sku1"))
                .price(CartItemPrice.of(BigDecimal.valueOf(10)))
                .quantity(CartItemQuantity.of(10l))
                .stockAmount(CartItemStockAmount.of(11l)).build();

        Assert.assertTrue(cart.addItem(item1));
        Assert.assertEquals(BigDecimal.valueOf(10), cart.getTotalPrice().asBigDecimal());
        CartItem item2 = CartItem.builder()
                .sku(CartItemSku.of("sku1"))
                .price(CartItemPrice.of(BigDecimal.valueOf(5)))
                .quantity(CartItemQuantity.of(3l))
                .stockAmount(CartItemStockAmount.of(5l)).build();
        Assert.assertTrue(cart.editItem(item2));
        Assert.assertEquals(BigDecimal.valueOf(5), cart.getTotalPrice().asBigDecimal());

        cart.setCartStatus(CartStatus.builder().checkedOut(CartStatusCheckedOut.of(true)).build());
        Assert.assertFalse(cart.editItem(item2));

    }
}
