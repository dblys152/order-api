package com.ys.order.domain.core;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderId {

    @NotNull
    String id;
}
