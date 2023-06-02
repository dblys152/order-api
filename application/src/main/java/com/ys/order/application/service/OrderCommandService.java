package com.ys.order.application.service;

import com.ys.order.application.port.in.CompleteOrderCommand;
import com.ys.order.application.port.in.CompleteOrderUseCase;
import com.ys.order.application.port.out.LoadOrderPort;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.application.service.exception.OrderNotFoundException;
import com.ys.order.domain.core.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandService implements CompleteOrderUseCase {

    private final LoadOrderPort loadOrderPort;
    private final RecordOrderPort recordOrderPort;

    @Override
    public Order complete(CompleteOrderCommand command) {
        Order preOrdered = loadOrderPort.findById(command.getOrderId());
        preOrdered.complete();

        Order ordered = recordOrderPort.save(preOrdered);

        return ordered;
    }
}
