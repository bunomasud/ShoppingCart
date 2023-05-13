package de.inits.io.shoppingcart.product.domain;

import de.inits.io.shoppingcart.product.domain.entity.Product;
import de.inits.io.shoppingcart.product.domain.valueobjects.EuropeanArticleNumber;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductName;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;


public class ProductInventoryTest {

    @Test
    public void testIfProductValid() {
        Product product = Product.builder()
                .sku(StockKeepingUnit.of(UUID.randomUUID()))
                .name(ProductName.of("someName"))
                .price(ProductPrice.of(BigDecimal.valueOf(10)))
                .stockAmount(ProductStockAmount.of(100L))
                .europeanArticleNumberList(List.of(EuropeanArticleNumber.of("123")))
                .build();
        Assert.assertTrue(product.isProductValid());

    }

    @Test
    public void testIfProductInValid() {
        Product product = Product.builder()
                .sku(StockKeepingUnit.of(UUID.randomUUID()))
                .name(ProductName.of("someName"))
                .price(ProductPrice.of(BigDecimal.valueOf(0)))
                .stockAmount(ProductStockAmount.of(100L))
                .europeanArticleNumberList(List.of(EuropeanArticleNumber.of("123")))
                .build();
        Assert.assertFalse(product.isProductValid());
        List<EuropeanArticleNumber> emptyEanList = new ArrayList<>();
        Product product2 = Product.builder()
                .sku(StockKeepingUnit.of(UUID.randomUUID()))
                .name(ProductName.of("someName"))
                .price(ProductPrice.of(BigDecimal.valueOf(0)))
                .stockAmount(ProductStockAmount.of(100L))
                .europeanArticleNumberList(emptyEanList)
                .build();
        Assert.assertFalse(product2.isProductValid());
    }

}
