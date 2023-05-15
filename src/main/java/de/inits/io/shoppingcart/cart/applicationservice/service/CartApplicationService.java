package de.inits.io.shoppingcart.cart.applicationservice.service;

import de.inits.io.shoppingcart.cart.applicationservice.commands.CreateCartCommand;
import de.inits.io.shoppingcart.cart.applicationservice.commands.SetCartAsCheckedOutCommand;
import de.inits.io.shoppingcart.cart.applicationservice.commands.SetCartAsCurrentCommand;
import de.inits.io.shoppingcart.cart.applicationservice.commands.UpdateCartCommand;
import de.inits.io.shoppingcart.cart.applicationservice.querys.GetCartQuery;
import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.domain.entity.CartItem;
import de.inits.io.shoppingcart.cart.domain.entity.CartStatus;
import de.inits.io.shoppingcart.cart.domain.services.CartDomainService;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartItemStockAmount;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCheckedOut;
import de.inits.io.shoppingcart.cart.domain.valueobjects.CartStatusCurrent;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.port.CartJpaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartApplicationService {

    private final CartJpaRepository repositoryAdapter;
    private final CartDomainService cartDomainService;

    public CartApplicationService(CartJpaRepository repositoryAdapter, CartDomainService cartDomainService) {
        this.repositoryAdapter = repositoryAdapter;
        this.cartDomainService = cartDomainService;
    }

    public Cart getCurrentCart() {
        //Hack Multiple as Current possible, must save others as non-current??
        return repositoryAdapter.getCartByStatusCurrent();
    }

    public Cart getCart(GetCartQuery cartQuery) {
        return repositoryAdapter.getCart(cartQuery.id());
    }

    public Cart createNewCart(CreateCartCommand createCartCommand) {
        Cart cart = new Cart();
        cart.setCartStatus(getDefaultCartStatus());
        createCartCommand.cartItems()
                .forEach(cartItem -> cart.addItem(getCartItemsWithPriceAndStock(cartItem)));
        return repositoryAdapter.saveCart(cart);
    }

    public void editCart(UpdateCartCommand updateCartCommand) {
        Cart editCart = repositoryAdapter.getCart(updateCartCommand.cartId());
        if (!editCart.getCartStatus().getCheckedOut()
                .asBoolean()) {// get me into domain? logic belongs to domain/service?
            updateCartCommand.cartItems()
                    .forEach(cartItem -> editCart.editItem(getCartItemsWithPriceAndStock(cartItem)));
            repositoryAdapter.saveCart(editCart);
        }
    }

    public void checkOutCart(SetCartAsCheckedOutCommand setCartAsCheckedOutCommand) {
        Cart checkOutCart = repositoryAdapter.getCart(setCartAsCheckedOutCommand.cartId());
        checkOutCart.getCartStatus().setCheckedOut(CartStatusCheckedOut.of(true));
        repositoryAdapter.saveCart(checkOutCart);
        adjustStock(checkOutCart.getCartItems());
    }

    public void setCartAsCurrent(SetCartAsCurrentCommand setCartAsCurrentCommand) {
        Cart checkOutCart = repositoryAdapter.getCart(setCartAsCurrentCommand.cartId());
        checkOutCart.getCartStatus().setCartStatusCurrent(CartStatusCurrent.of(true));
        repositoryAdapter.saveCart(checkOutCart);
    }

    private CartStatus getDefaultCartStatus() {
        return CartStatus.builder()
                .cartStatusCurrent(CartStatusCurrent.of(false))
                .checkedOut(CartStatusCheckedOut.of(false)).build();
    }

    private CartItem getCartItemsWithPriceAndStock(CartItem cartItem) {
        return CartItem.builder()
                .sku(cartItem.getSku())
                .quantity(cartItem.getQuantity())
                .price(cartDomainService.getItemPrice(cartItem.getSku()))
                .stockAmount(cartDomainService.getAvailableStock(cartItem.getSku())).build();
    }

    private void adjustStock(List<CartItem> cartItemList) {
        for (CartItem item : cartItemList) {
            CartItemStockAmount stockAmount = cartDomainService.getAvailableStock(item.getSku());
            cartDomainService.setStockAmount(item.getSku(),
                    CartItemStockAmount.of(stockAmount.asLong() - item.getQuantity().asLong()));
        }
    }
}
