package com.ys.order.application.port.in;

import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import com.ys.order.domain.core.Orders;
import com.ys.refs.user.domain.UserId;

public interface GetOrderQuery {

    Order getOrder(OrderId orderId);
    Orders getOrders(UserId userId);
}
