package com.ys.order.domain.core;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Address {

    @NotNull
    String zipcode;
    @NotNull
    String firstLine;
    @NotNull
    String secondLine;
}
