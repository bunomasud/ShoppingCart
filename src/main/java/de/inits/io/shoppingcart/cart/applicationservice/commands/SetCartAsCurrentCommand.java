package de.inits.io.shoppingcart.cart.applicationservice.commands;

import java.util.UUID;
import lombok.Builder;

public record SetCartAsCurrentCommand(UUID cartId) {

    @Builder
    public SetCartAsCurrentCommand {

    }
}
