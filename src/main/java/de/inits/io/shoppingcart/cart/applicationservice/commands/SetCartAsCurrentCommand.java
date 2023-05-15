package de.inits.io.shoppingcart.cart.applicationservice.commands;

import java.util.UUID;
import lombok.Builder;
public record SetCartAsCurrentCommand(long cartId) {

    @Builder
    public SetCartAsCurrentCommand {

    }
}
