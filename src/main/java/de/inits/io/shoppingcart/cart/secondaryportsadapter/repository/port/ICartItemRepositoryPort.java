package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.port;

import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartItemOrm;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartOrm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartItemRepositoryPort extends CrudRepository<CartItemOrm, Long> {

}
