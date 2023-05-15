package de.inits.io.shoppingcart.cart.applicationservice.commands;

import lombok.Builder;
public record SetCartAsCheckedOutCommand(long cartId) {

    @Builder
    public SetCartAsCheckedOutCommand {

    }
}
