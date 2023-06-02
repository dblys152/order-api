package com.ys.order.application.port.out;

import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import com.ys.order.domain.core.Orders;
import com.ys.refs.user.domain.UserId;

public interface LoadOrderPort {

    Order findById(OrderId orderId);
    Orders findAllByOrdererUserId(UserId userId);
}
