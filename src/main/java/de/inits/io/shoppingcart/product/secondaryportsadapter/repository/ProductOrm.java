package de.inits.io.shoppingcart.product.secondaryportsadapter.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ProductOrm")
@Table(name = "product")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrm {

    @Id
    private UUID sku;

    @Column(name = "name")
    private String name;
    //hack, should be a one to many
    @Column(name = "ean")
    private String ean;

    @Column(name = "price", precision = 16, scale = 4)
    private BigDecimal price;

    @Column(name = "stock")
    private Long stock;

}
