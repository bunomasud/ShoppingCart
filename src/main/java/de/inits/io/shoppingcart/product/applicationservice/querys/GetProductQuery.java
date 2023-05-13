package de.inits.io.shoppingcart.product.applicationservice.querys;

import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import lombok.Builder;

public record GetProductQuery(StockKeepingUnit sku) {

    @Builder
    public GetProductQuery {
    }
}
