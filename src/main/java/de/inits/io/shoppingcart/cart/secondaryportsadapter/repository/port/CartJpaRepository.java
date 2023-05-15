package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.port;

import de.inits.io.shoppingcart.cart.domain.aggregateroot.Cart;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartOrm;
import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.adapter.mapper.CartJpaAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartJpaRepository {

    private final ICartRepositoryPort iCartRepositoryPort;
    private final ICartItemRepositoryPort iCartItemRepositoryPort;
    private final CartJpaAdapter cartJpaAdapter;

    public CartJpaRepository(ICartRepositoryPort iCartRepositoryPort, ICartItemRepositoryPort iCartItemRepositoryPort,
            CartJpaAdapter cartJpaAdapter) {
        this.iCartRepositoryPort = iCartRepositoryPort;
        this.iCartItemRepositoryPort = iCartItemRepositoryPort;
        this.cartJpaAdapter = cartJpaAdapter;
    }

    public Cart saveCart(Cart cart) {
        CartOrm orm = cartJpaAdapter.toOrm(cart);
        orm.getItems().stream().forEach(iCartItemRepositoryPort::save);
        iCartRepositoryPort.save(orm);
        return cartJpaAdapter.toDomain(orm);
    }

    public Cart getCartByStatusCurrent() {
        return cartJpaAdapter.toDomain(
                iCartRepositoryPort.findCartOrmsByCurrentIsTrueAndAndCheckedOutIsFalse().stream().findFirst().get());
    }

    public Cart getCart(Long id) {
        return cartJpaAdapter.toDomain(iCartRepositoryPort.findById(id).get());//TODO check isPresent()
    }
}
