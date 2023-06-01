package com.ys.order.domain.core;

import com.refs.productoption.domain.ProductOptionId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderLine {

    @NotNull
    private ProductOptionId productOptionId;
    @NotNull
    private Money price;
    @NotNull
    private int quantity;
    @NotNull
    private Money amount;

    private OrderLine(ProductOptionId productOptionId, Money price, int quantity) {
        this.productOptionId = productOptionId;
        this.price = price;
        this.quantity = quantity;
        this.amount = calculateAmount();
    }

    public static OrderLine of(ProductOptionId productOptionId, Money price, int quantity) {
        return new OrderLine(productOptionId, price, quantity);
    }

    private Money calculateAmount() {
        return this.price.multiply(this.quantity);
    }
}
