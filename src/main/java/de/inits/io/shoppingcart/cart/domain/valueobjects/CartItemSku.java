package de.inits.io.shoppingcart.cart.domain.valueobjects;

import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class CartItemSku {

    private String sku;

    private CartItemSku(String sku) {
        this.sku = sku;
    }

    public static CartItemSku of(String sku) {
        return new CartItemSku(sku);
    }

    public String asString() {
        return sku;
    }

}
