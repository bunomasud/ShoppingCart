package de.inits.io.shoppingcart.cart.applicationservice.commands;

import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import java.util.List;
import lombok.Builder;

public record CreateCartCommand(List<CartItem> cartItems) {

    @Builder
    public CreateCartCommand {

    }
}
