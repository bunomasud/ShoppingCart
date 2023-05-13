package de.inits.io.shoppingcart.cart.applicationservice.querys;

import java.util.UUID;
import lombok.Builder;

public record GetCartQuery(UUID ID) {

    @Builder
    public GetCartQuery {

    }
}
