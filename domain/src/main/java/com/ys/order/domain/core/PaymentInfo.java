package com.ys.order.domain.core;

import lombok.Value;

@Value(staticConstructor = "of")
public class PaymentInfo {

    PaymentMethod method;
    Money amount;
}
