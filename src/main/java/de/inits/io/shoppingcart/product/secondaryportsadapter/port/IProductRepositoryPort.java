package de.inits.io.shoppingcart.product.secondaryportsadapter.port;

import de.inits.io.shoppingcart.product.secondaryportsadapter.repository.ProductOrm;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductRepositoryPort extends CrudRepository<ProductOrm, UUID> {

}
