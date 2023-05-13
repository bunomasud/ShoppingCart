package de.inits.io.shoppingcart.product.applicationservice.querys;

import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import lombok.Builder;

public record ProductStockQuery(StockKeepingUnit sku) {

    @Builder
    public ProductStockQuery {
    }
}
