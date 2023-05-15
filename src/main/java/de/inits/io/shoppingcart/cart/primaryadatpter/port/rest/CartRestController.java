package de.inits.io.shoppingcart.cart.primaryadatpter.port.rest;
import de.inits.io.shoppingcart.cart.applicationservice.commands.CreateCartCommand;
import de.inits.io.shoppingcart.cart.applicationservice.commands.SetCartAsCheckedOutCommand;
import de.inits.io.shoppingcart.cart.applicationservice.commands.SetCartAsCurrentCommand;
import de.inits.io.shoppingcart.cart.applicationservice.commands.UpdateCartCommand;
import de.inits.io.shoppingcart.cart.applicationservice.querys.GetCartQuery;
import de.inits.io.shoppingcart.cart.applicationservice.service.CartApplicationService;
import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.primaryadatpter.adapter.CartRestControllerAdapter;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartRestController {

    private CartRestControllerAdapter cartRestControllerAdapter;
    private CartApplicationService cartApplicationService;

    public CartRestController(CartRestControllerAdapter cartRestControllerAdapter,
            CartApplicationService cartApplicationService) {
        this.cartRestControllerAdapter = cartRestControllerAdapter;
        this.cartApplicationService = cartApplicationService;
    }

    @GetMapping("/cart/{id}/")
    public CartResponseData getCart(@PathVariable String id) {
        Cart cart = cartApplicationService.getCart(
                GetCartQuery.builder().id(Long.valueOf(id)).build());//number format exception??
        return cartRestControllerAdapter.adaptToCartResponse(cart);
    }

    @PostMapping("/cart/")
    public CartResponseData createCart(@RequestBody CartRequestData cartRequestData) {
        Cart cart = cartApplicationService.createNewCart(CreateCartCommand.builder()
                .cartItems(cartRestControllerAdapter.adaptToCartItems(cartRequestData.getItems())).build());
        return cartRestControllerAdapter.adaptToCartResponse(cart);
    }

    @PostMapping("/cart/{id}/setStatus")
    public void setCartStatus(@PathVariable long id, @RequestParam("status") CartStatus cartStatus) {
        switch (cartStatus) {
            case CURRENT -> cartApplicationService.setCartAsCurrent(
                    SetCartAsCurrentCommand.builder().cartId(id).build());
            case CHECKED_OUT ->
                    cartApplicationService.checkOutCart(SetCartAsCheckedOutCommand.builder().cartId(id).build());
        }
    }

    @PatchMapping("/cart/{id}/")
    public void editCart(@PathVariable long id, @RequestBody CartRequestData cartRequestData) {
        cartApplicationService.editCart(UpdateCartCommand.builder()
                .cartId(id)
                .cartItems(cartRestControllerAdapter.adaptToCartItems(cartRequestData.getItems()))
                .build());
    }

}
