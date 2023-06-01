package com.ys.order.domain.core;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Money {

    @NotNull
    int value;

    public Money add(Money money) {
        return Money.of(this.value + money.value);
    }

    public Money multiply(int multiplier) {
        return new Money(this.value * multiplier);
    }
}
