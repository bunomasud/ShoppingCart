package de.inits.io.shoppingcart.product.domain.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class EuropeanArticleNumber {

    String ean;

    private EuropeanArticleNumber(String ean) {
        this.ean = ean;
    }

    public static EuropeanArticleNumber of(String ean) {
        return new EuropeanArticleNumber(ean);
    }

    public String asString() {
        return ean;
    }

    public boolean isEanEmpty() {
        return ean.isEmpty();
    }
}
