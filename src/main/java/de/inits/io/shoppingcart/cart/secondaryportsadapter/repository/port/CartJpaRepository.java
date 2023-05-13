package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.port;

import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.adapter.mapper.CartJpaAdapter;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartJpaRepository {

    private final ICartRepositoryPort iCartRepositoryPort;
    private final CartJpaAdapter cartJpaAdapter;

    public CartJpaRepository(ICartRepositoryPort iCartRepositoryPort, CartJpaAdapter cartJpaAdapter) {
        this.iCartRepositoryPort = iCartRepositoryPort;
        this.cartJpaAdapter = cartJpaAdapter;
    }

    public void saveCart(Cart cart) {
        iCartRepositoryPort.save(cartJpaAdapter.toOrm(cart));
    }

    public Cart getCartByStatusCurrent() {
        return cartJpaAdapter.toDomain(
                iCartRepositoryPort.findCartOrmsByCurrentIsTrueAndAndCheckedOutIsFalse().stream().findFirst().get());
    }

    public Cart getCart(UUID id) {
        return cartJpaAdapter.toDomain(iCartRepositoryPort.findById(id).get());//TODO check isPresent()
    }
}
