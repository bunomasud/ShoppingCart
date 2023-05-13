package de.inits.io.shoppingcart.product.applicationservice.commands;

import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import lombok.Builder;

public record RemoveProductCommand(StockKeepingUnit sku) {

    @Builder
    public RemoveProductCommand {

    }

}