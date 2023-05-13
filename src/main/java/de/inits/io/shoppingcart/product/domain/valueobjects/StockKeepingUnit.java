package de.inits.io.shoppingcart.product.domain.valueobjects;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class StockKeepingUnit {

    private UUID sku;

    private StockKeepingUnit(UUID sku) {
        this.sku = sku;
    }

    public static StockKeepingUnit of(UUID sku) {
        return new StockKeepingUnit(sku);
    }

    public UUID asUUID() {
        return sku;
    }

    public boolean isSkuEmpty() {
        return null == sku;
    }
}
