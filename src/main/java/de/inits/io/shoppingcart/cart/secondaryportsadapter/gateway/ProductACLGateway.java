package de.inits.io.shoppingcart.cart.secondaryportsadapter.gateway;

import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.gateway.port.ICartProductGatewayPort;
import de.inits.io.shoppingcart.product.applicationservice.anticorruptionlayer.ProductACL;
import org.springframework.stereotype.Service;

@Service
public class ProductACLGateway implements ICartProductGatewayPort {

    private final ProductACL productACL;

    public ProductACLGateway(ProductACL productACL) {
        this.productACL = productACL;
    }

    @Override
    public CartItemPrice getPrice(CartItemSku sku) {
        return productACL.getPrice(sku);
    }

    @Override
    public CartItemStockAmount getStock(CartItemSku sku) {
        return productACL.getStock(sku);
    }

    @Override
    public void setStock(CartItemSku sku, CartItemStockAmount stockAmount) {
        productACL.setStock(sku, stockAmount);
    }
}
