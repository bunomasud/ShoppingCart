package de.inits.io.shoppingcart.product.primaryadatpter.port.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductData {

    private UUID sku;
    private String name;
    private BigDecimal price;
    private List<String> eans;
    private long stock;
}
