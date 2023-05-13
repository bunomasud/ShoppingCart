package de.inits.io.shoppingcart.product.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class ProductName {

    private String name;

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName of(String name) {
        return new ProductName(name);
    }

    public String asString() {
        return name;
    }

    public boolean isNameEmpty() {
        return name.isEmpty();
    }
}
