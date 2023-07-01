package com.ys.order.application.service;

import com.ys.infra.message.DomainEvent;
import com.ys.order.application.port.in.CompleteOrderCommand;
import com.ys.order.application.port.in.CompleteOrderUseCase;
import com.ys.order.application.port.out.LoadOrderPort;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.event.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderCommandService implements CompleteOrderUseCase {

    private final LoadOrderPort loadOrderPort;
    private final RecordOrderPort recordOrderPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Order complete(CompleteOrderCommand command) {
        Order preOrdered = loadOrderPort.findById(command.getOrderId());
        preOrdered.complete();

        Order ordered = recordOrderPort.save(preOrdered);

        OrderCompletedEvent event = OrderCompletedEvent.fromDomain(ordered);
        DomainEvent<OrderCompletedEvent> domainEvent = new DomainEvent(
                OrderCompletedEvent.class.getName(),
                event
        );
        eventPublisher.publishEvent(domainEvent);

        return ordered;
    }
}
