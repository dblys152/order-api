package com.refs.productoption.domain;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value(staticConstructor = "of")
public class ProductOptionId {

    @NotNull
    String id;
}
