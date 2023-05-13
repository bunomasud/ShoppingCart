package de.inits.io.shoppingcart.product.domain.entity;

import de.inits.io.shoppingcart.product.domain.valueobjects.EuropeanArticleNumber;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductName;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Builder
public class Product {

    private StockKeepingUnit sku;
    private ProductStockAmount stockAmount;
    private ProductPrice price;
    private ProductName name;
    private List<EuropeanArticleNumber> europeanArticleNumberList;

    public boolean isProductValid() {
        return !this.sku.isSkuEmpty()
                && !this.name.isNameEmpty()
                && !this.europeanArticleNumberList.isEmpty()
                && this.price.isPriceNonZero();

    }

    public boolean isProductStockZero() {
        return this.stockAmount.isStockAvailable();
    }

    public boolean isStockAmountAvailable(Long amount) {
        return this.stockAmount.asLong() < amount;
    }

    public void increaseProductStock(Long addAmount) { //setStock enough just helpers..
        this.stockAmount = ProductStockAmount.of(this.stockAmount.asLong() + addAmount);
    }

    public void decreaseProductStock(Long delAmount) {
        this.stockAmount = ProductStockAmount.of(this.stockAmount.asLong() - delAmount);
    }

}
