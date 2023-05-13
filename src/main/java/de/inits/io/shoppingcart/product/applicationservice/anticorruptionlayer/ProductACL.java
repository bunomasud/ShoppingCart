package de.inits.io.shoppingcart.product.applicationservice.anticorruptionlayer;

import de.inits.io.shoppingcart.product.applicationservice.anticorruptionlayer.mapper.ContextMap;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.product.applicationservice.service.ProductApplicationService;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductPrice;
import de.inits.io.shoppingcart.product.domain.valueobjects.ProductStockAmount;
import org.springframework.stereotype.Service;

@Service
public class ProductACL {

    private final ProductApplicationService productApplicationService;
    private final ContextMap contextMap;

    public ProductACL(ProductApplicationService productApplicationService, ContextMap contextMap) {
        this.productApplicationService = productApplicationService;
        this.contextMap = contextMap;
    }

    public CartItemPrice getPrice(CartItemSku sku) {
        ProductPrice productPrice = productApplicationService.getProductPrice(contextMap.toProductPriceQuery(sku));
        return contextMap.toCartItemPrice(productPrice);
    }

    public CartItemStockAmount getStock(CartItemSku sku) {
        ProductStockAmount stockAmount = productApplicationService.getProductStock(contextMap.toProductStockQuery(sku));
        return contextMap.toCartItemPrice(stockAmount);
    }

    public void setStock(CartItemSku sku, CartItemStockAmount amount) {
        productApplicationService.setProductStock(contextMap.toSetProductStockCommand(sku, amount));
    }

}
