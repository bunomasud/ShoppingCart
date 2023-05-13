package de.inits.io.shoppingcart.product.applicationservice.commands;

import de.inits.io.shoppingcart.product.domain.valueobjects.EuropeanArticleNumber;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductName;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import lombok.Builder;

public record EditProductCommand(StockKeepingUnit sku,
                                 ProductPrice price,
                                 ProductName name,
                                 ProductStockAmount stockAmount,
                                 EuropeanArticleNumber europeanArticleNumber) {

    @Builder
    public EditProductCommand {

    }

}
