package com.ys.order.application.port.in;

import com.ys.order.domain.core.Order;

public interface CompleteOrderUseCase {
    Order complete(CompleteOrderCommand command);
}
