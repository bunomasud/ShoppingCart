package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.port;

import de.inits.io.shoppingcart.cart.secondaryportsadapter.repository.CartOrm;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepositoryPort extends CrudRepository<CartOrm, UUID> {

    List<CartOrm> findCartOrmsByCurrentIsTrueAndAndCheckedOutIsFalse();

}
