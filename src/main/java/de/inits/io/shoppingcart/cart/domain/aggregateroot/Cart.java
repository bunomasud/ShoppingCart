package de.inits.io.shoppingcart.cart.domain.aggregateroot;

import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.entity.CartStatus;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartTotalPrice;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@AllArgsConstructor
@Data
@Setter(AccessLevel.NONE)
@Builder
public class Cart {

    private UUID cartID;
    private List<CartItem> cartItems;
    private CartStatus cartStatus;
    private CartTotalPrice totalPrice;

    public boolean addItem(CartItem item) {
        if (isItemStockAvailable(item)) {
            cartItems.add(item);
            totalPrice = CartTotalPrice.of(totalPrice.asBigDecimal().add(item.getPrice().asBigDecimal()));
            return true;
        }
        return false;
    }

    public boolean editItem(CartItem editItem) {

        Optional<CartItem> itemToEditOptional = getEditOptional(editItem);
        if (itemToEditOptional.isPresent()) {
            CartItem itemToEdit = itemToEditOptional.get();
            if (isItemStockAvailable(editItem)) {
                resetTotalPrice(editItem, itemToEdit);
                itemToEdit.setQuantity(editItem.getQuantity());
                itemToEdit.setSku(editItem.getSku());
                itemToEdit.setPrice(editItem.getPrice());
                itemToEdit.setStockAmount(itemToEdit.getStockAmount());
                return true;
            }

        }
        return false;
    }

    private void resetTotalPrice(CartItem editItem, CartItem itemToEdit) {
        totalPrice = CartTotalPrice.of(
                totalPrice.asBigDecimal().subtract(itemToEdit.getPrice().asBigDecimal()));
        totalPrice = CartTotalPrice.of(totalPrice.asBigDecimal().add(editItem.getPrice().asBigDecimal()));
    }

    private boolean isItemStockAvailable(CartItem cartItem) {
        return cartItem.getStockAmount().isMoreThanZero() && cartItem.getStockAmount().asLong() > cartItem.getQuantity()
                .asLong();
    }

    private Optional<CartItem> getEditOptional(CartItem editItem) {
        Optional<CartItem> itemToEditOptional = cartItems.stream()
                .filter(cartItem -> cartItem.getItemId().equals(editItem.getItemId())).findFirst();
        return itemToEditOptional;
    }


}
