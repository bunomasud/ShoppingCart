package de.inits.io.shoppingcart.cart.domain.aggregateroot;

import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.entity.CartStatus;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartTotalPrice;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Cart {

    private Long cartID;
    private List<CartItem> cartItems = new ArrayList<>();
    private CartStatus cartStatus;
    private CartTotalPrice totalPrice = CartTotalPrice.of(BigDecimal.valueOf(0));

    public boolean addItem(CartItem item) {
        if (isItemStockAvailable(item)) {
            getCartItems().add(item);
            setTotalPrice(CartTotalPrice.of(totalPrice.asBigDecimal().add(item.getPrice().asBigDecimal())));
            return true;
        }
        return false;
    }

    public void editItem(CartItem editItem) {

        Optional<CartItem> itemToEditOptional = getEditOptional(editItem);
        if (itemToEditOptional.isPresent()) {
            CartItem itemToEdit = itemToEditOptional.get();
            if (isItemStockAvailable(editItem)) {
                resetTotalPrice(editItem, itemToEdit);
                itemToEdit.setQuantity(editItem.getQuantity());
                itemToEdit.setSku(editItem.getSku());
                itemToEdit.setPrice(editItem.getPrice());
                itemToEdit.setStockAmount(itemToEdit.getStockAmount());
            }
        } else {
            addItem(editItem);
        }
    }

    private void resetTotalPrice(CartItem editItem, CartItem itemToEdit) {
        totalPrice = CartTotalPrice.of(
                totalPrice.asBigDecimal().subtract(itemToEdit.getPrice().asBigDecimal()));
        totalPrice = CartTotalPrice.of(totalPrice.asBigDecimal().add(editItem.getPrice().asBigDecimal()));
    }

    private boolean isItemStockAvailable(CartItem cartItem) {
        return cartItem.getStockAmount().isMoreThanZero()
                && cartItem.getStockAmount().asLong() >= cartItem.getQuantity()
                .asLong();
    }

    private Optional<CartItem> getEditOptional(CartItem editItem) {
        return getCartItems().stream()
                .filter(cartItem -> cartItem.getSku().asString().equals(editItem.getSku().asString())).findFirst();
    }


}
