package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CartItemOrm")
@Table(name = "cart_item")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemOrm {

    @Id
    private UUID id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private long quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartOrm cart;

}
