package com.ys.order.application.listener;

import com.ys.infra.message.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class DomainEventListener {

    @TransactionalEventListener
    public void on(DomainEvent event) {
        log.info("Receive DomainEvent name: {} OccurredAt: {}", event.getType(), event.getOccurredAt());
    }
}
