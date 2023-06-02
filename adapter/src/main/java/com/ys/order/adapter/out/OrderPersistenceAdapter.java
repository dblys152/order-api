package com.ys.order.adapter.out;

import com.ys.order.adapter.out.persistence.OrderEntity;
import com.ys.order.adapter.out.persistence.OrderEntityRepository;
import com.ys.order.application.port.out.LoadOrderPort;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.application.service.exception.OrderNotFoundException;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import com.ys.order.domain.core.Orders;
import com.ys.refs.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements RecordOrderPort, LoadOrderPort {

    private final OrderEntityRepository repository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = repository.save(OrderEntity.fromDomain(order));
        return orderEntity.toDomain();
    }

    @Override
    public Order findById(OrderId orderId) {
        OrderEntity orderEntity = repository.findById(orderId.getId())
                .orElseThrow(OrderNotFoundException::new);
        return orderEntity.toDomain();
    }

    @Override
    public Orders findAllByOrdererUserId(UserId userId) {
        List<OrderEntity> orderEntityList = repository.findAllByOrdererUserId(userId.getId());
        return Orders.of(orderEntityList.stream()
                .map(o -> o.toDomain())
                .collect(Collectors.toList()));
    }
}
