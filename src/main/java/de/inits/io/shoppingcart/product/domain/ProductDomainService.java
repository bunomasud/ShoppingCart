package de.inits.io.shoppingcart.product.domain;

import de.inits.io.shoppingcart.product.secondaryportsadapter.port.IProductRepositoryPort;

public class ProductDomainService {

    private IProductRepositoryPort repositoryPort;

    public ProductDomainService(IProductRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

}
