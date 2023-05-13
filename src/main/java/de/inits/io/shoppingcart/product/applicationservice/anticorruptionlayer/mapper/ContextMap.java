package de.inits.io.shoppingcart.product.applicationservice.anticorruptionlayer.mapper;

import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.product.applicationservice.commands.setProductStockCommand;
import de.inits.io.shoppingcart.product.applicationservice.querys.ProductPricingQuery;
import de.inits.io.shoppingcart.product.applicationservice.querys.ProductStockQuery;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ContextMap {

    public ProductStockQuery toProductStockQuery(CartItemSku sku) {
        return ProductStockQuery.builder()
                .sku(StockKeepingUnit.of(UUID.fromString(sku.asString()))).build();
    }

    public setProductStockCommand toSetProductStockCommand(CartItemSku sku, CartItemStockAmount amount) {
        return setProductStockCommand.builder()
                .sku(StockKeepingUnit.of(UUID.fromString(sku.asString())))
                .stockAmount(ProductStockAmount.of(amount.asLong()))
                .build();
    }

    public ProductPricingQuery toProductPriceQuery(CartItemSku sku) {
        return ProductPricingQuery.builder()
                .sku(StockKeepingUnit.of(UUID.fromString(sku.asString()))).build();
    }

    public CartItemPrice toCartItemPrice(ProductPrice productPrice) {
        return CartItemPrice.of(productPrice.asBigDecimal());
    }

    public CartItemStockAmount toCartItemPrice(ProductStockAmount stockAmount) {
        return CartItemStockAmount.of(stockAmount.asLong());
    }

}
