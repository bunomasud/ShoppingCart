package de.inits.io.shoppingcart.product.primaryadatpter.port.adapter;

import de.inits.io.shoppingcart.product.applicationservice.commands.AddProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.EditProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.RemoveProductCommand;
import de.inits.io.shoppingcart.product.applicationservice.commands.setProductStockCommand;
import de.inits.io.shoppingcart.product.applicationservice.querys.ProductPricingQuery;
import de.inits.io.shoppingcart.product.applicationservice.querys.ProductStockQuery;
import de.inits.io.shoppingcart.product.domain.aggregateroot.ProductInventory;
import de.inits.io.shoppingcart.product.domain.entity.Product;
import de.inits.io.shoppingcart.product.domain.valueobjects.EuropeanArticleNumber;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductName;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import de.inits.io.shoppingcart.product.domain.valueobjects.StockKeepingUnit;
import de.inits.io.shoppingcart.product.primaryadatpter.port.rest.InventoryData;
import de.inits.io.shoppingcart.product.primaryadatpter.port.rest.ProductData;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductRestControllerAdapter {

    public AddProductCommand adaptToAddProductCommand(ProductData request) {
        return AddProductCommand.builder()
                .name(ProductName.of(request.getName()))
                .price(ProductPrice.of(request.getPrice()))
                .stockAmount(ProductStockAmount.of(request.getStock()))
                .sku(StockKeepingUnit.of(request.getSku()))
                .price(ProductPrice.of(request.getPrice())).build();

    }

    public RemoveProductCommand adaptToRemoveProductCommand(UUID sku) {
        return RemoveProductCommand.builder().sku(StockKeepingUnit.of(sku)).build();
    }

    public ProductPricingQuery adaptToProductPricingQuery(UUID sku) {
        return ProductPricingQuery.builder().sku(StockKeepingUnit.of(sku)).build();
    }

    public ProductStockQuery adaptToProductStockQuery(UUID sku) {
        return ProductStockQuery.builder().sku(StockKeepingUnit.of(sku)).build();
    }

    public setProductStockCommand adaptToSetProductCommand(UUID sku, Long stock) {
        return setProductStockCommand.builder()
                .sku(StockKeepingUnit.of(sku))
                .stockAmount(ProductStockAmount.of(stock)).build();
    }

    public EditProductCommand adaptToEditProductCommand(ProductData request) {
        return EditProductCommand.builder()
                .name(ProductName.of(request.getName()))
                .price(ProductPrice.of(request.getPrice()))
                .stockAmount(ProductStockAmount.of(request.getStock()))
                .sku(StockKeepingUnit.of(request.getSku()))
                .price(ProductPrice.of(request.getPrice())).build();
    }

    public InventoryData adaptToInventoryData(ProductInventory inventory) {
        List<ProductData> productList = inventory.getProducts().stream().map(this::adaptToProductData)
                .collect(Collectors.toList());
        return InventoryData.builder().products(productList).build();
    }

    private ProductData adaptToProductData(Product product) {
        return ProductData.builder()
                .name(product.getName().asString())
                .price(product.getPrice().asBigDecimal())
                .eans(product.getEuropeanArticleNumberList().stream()
                        .map(EuropeanArticleNumber::asString).collect(Collectors.toList()))
                .stock(product.getStockAmount().asLong())
                .sku(product.getSku().asUUID()).build();
    }

}
