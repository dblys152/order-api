package com.ys.order.application.port.out;

import com.ys.order.domain.core.Order;

public interface RecordOrderPort {

    Order save(Order order);
}
