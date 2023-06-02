package com.ys.order.application.service;

import com.ys.order.application.port.in.PreOrderCommand;
import com.ys.order.application.port.in.PreOrderUseCase;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.domain.core.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreOrderCommandService implements PreOrderUseCase {

    private final RecordOrderPort recordOrderPort;

    @Override
    public Order preOrder(PreOrderCommand command) {
        Order preOrdered = Order.preOrder(
                command.getOrderer(),
                command.getOrderLines(),
                command.getPaymentInfos(),
                command.getShippingInfo()
        );

        recordOrderPort.save(preOrdered);

        return preOrdered;
    }
}
