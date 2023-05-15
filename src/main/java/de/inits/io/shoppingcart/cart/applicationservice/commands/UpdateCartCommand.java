package de.inits.io.shoppingcart.cart.applicationservice.commands;

import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public record UpdateCartCommand(long cartId, List<CartItem> cartItems) {

    @Builder
    public UpdateCartCommand {

    }
}
