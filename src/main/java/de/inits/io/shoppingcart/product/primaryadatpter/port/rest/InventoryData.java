package de.inits.io.shoppingcart.product.primaryadatpter.port.rest;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class InventoryData {

    private List<ProductData> products;
}
