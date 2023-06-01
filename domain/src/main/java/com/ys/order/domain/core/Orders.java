package com.ys.order.domain.core;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Orders {

    List<Order> items;
}
