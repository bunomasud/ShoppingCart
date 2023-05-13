package de.inits.io.shoppingcart.product.secondaryportsadapter.adapter;

import de.inits.io.shoppingcart.product.domain.entity.Product;
import de.inits.io.shoppingcart.product.domain.valueobjects.EuropeanArticleNumber;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductName;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import de.inits.io.shoppingcart.product.secondaryportsadapter.repository.ProductOrm;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class ProductJpaAdapter {

    public ProductOrm toOrm(Product product) {
        return ProductOrm.builder()
                .sku(product.getSku().asUUID())
                .name(product.getName().asString())
                .price(product.getPrice().asBigDecimal())
                .stock(product.getStockAmount().asLong())
                .ean(product.getEuropeanArticleNumberList().stream().findFirst().get().asString()) //hack
                .build();


    }

    public Product toDomain(ProductOrm product) {
        //Hack
        ArrayList<EuropeanArticleNumber> easList = new ArrayList<>();
        easList.add(EuropeanArticleNumber.of(product.getEan()));
        return Product.builder()
                .sku(StockKeepingUnit.of(product.getSku()))
                .name(ProductName.of(product.getName()))
                .stockAmount(ProductStockAmount.of(product.getStock()))
                .price(ProductPrice.of(product.getPrice()))
                .europeanArticleNumberList(easList).build();
    }
}
