package de.inits.io.shoppingcart.cart.domain.services;

import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemPrice;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemSku;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.gateway.port.ICartProductGatewayPort;
import org.springframework.stereotype.Service;

@Service
public class CartDomainService {

    private final ICartProductGatewayPort productGatewayPort;

    public CartDomainService(ICartProductGatewayPort productGatewayPort) {
        this.productGatewayPort = productGatewayPort;
    }

    public CartItemStockAmount getAvailableStock(CartItemSku sku) {
        return productGatewayPort.getStock(sku);
    }

    public CartItemPrice getItemPrice(CartItemSku sku) {
        return productGatewayPort.getPrice(sku);
    }

    public void setStockAmount(CartItemSku sku, CartItemStockAmount stockAmount) {
        productGatewayPort.setStock(sku, stockAmount);
    }

}
