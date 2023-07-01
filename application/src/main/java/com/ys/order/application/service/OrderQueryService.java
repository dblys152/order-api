package com.ys.order.application.service;

import com.ys.order.application.port.in.GetOrderQuery;
import com.ys.order.application.port.out.LoadOrderPort;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import com.ys.order.domain.core.Orders;
import com.ys.refs.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderQueryService implements GetOrderQuery {

    private final LoadOrderPort loadOrderPort;

    @Override
    public Order getOrder(OrderId orderId) {
        return loadOrderPort.findById(orderId);
    }

    @Override
    public Orders getOrders(UserId userId) {
        return loadOrderPort.findAllByOrdererUserId(userId);
    }
}
