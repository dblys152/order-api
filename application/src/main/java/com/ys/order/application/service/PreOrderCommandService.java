package com.ys.order.application.service;

import com.ys.infra.message.DomainEvent;
import com.ys.order.application.port.in.PreOrderCommand;
import com.ys.order.application.port.in.PreOrderUseCase;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.event.OrderCompletedEvent;
import com.ys.order.domain.event.PreOrderedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PreOrderCommandService implements PreOrderUseCase {

    private final RecordOrderPort recordOrderPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Order preOrder(PreOrderCommand command) {
        Order preOrdered = Order.preOrder(
                command.getOrderer(),
                command.getOrderLines(),
                command.getPaymentInfos(),
                command.getShippingInfo()
        );

        Order savedPreOrder = recordOrderPort.save(preOrdered);

        PreOrderedEvent event = PreOrderedEvent.fromDomain(savedPreOrder);
        DomainEvent<PreOrderedEvent> domainEvent = new DomainEvent(
                PreOrderedEvent.class.getName(),
                event
        );
        eventPublisher.publishEvent(domainEvent);

        return preOrdered;
    }
}
