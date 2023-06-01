package com.ys.order.domain.core;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class OrderLines {

    @Size(min = 1)
    List<OrderLine> items;

    public Money calculateTotalAmount() {
        return this.items.stream()
                .map(OrderLine::getAmount)
                .reduce(Money::add)
                .orElse(Money.of(0));
    }
}
