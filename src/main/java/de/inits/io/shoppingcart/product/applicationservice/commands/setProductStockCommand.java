package de.inits.io.shoppingcart.product.applicationservice.commands;

import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import lombok.Builder;

public record setProductStockCommand(StockKeepingUnit sku, ProductStockAmount stockAmount) {

    @Builder
    public setProductStockCommand {

    }
}
