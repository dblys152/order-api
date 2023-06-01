package com.ys.order.domain.core;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ShippingInfo {

    @NotNull
    Receiver receiver;
    @NotNull
    Address address;
    String message;
}
