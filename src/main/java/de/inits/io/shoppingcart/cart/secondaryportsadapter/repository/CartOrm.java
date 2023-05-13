package de.inits.io.shoppingcart.cart.secondaryportsadapter.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CartOrm")
@Table(name = "cart")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartOrm {

    @Id
    private UUID id;

    @OneToMany(mappedBy = "cart")
    private Set<CartItemOrm> items;
    @Column(name = "is_checked_out")
    boolean checkedOut;
    @Column(name = "is_current")
    boolean current;


}
