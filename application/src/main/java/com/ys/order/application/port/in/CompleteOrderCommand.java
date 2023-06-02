package com.ys.order.application.port.in;

import com.ys.order.common.SelfValidating;
import com.ys.order.domain.core.OrderId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CompleteOrderCommand extends SelfValidating<CompleteOrderCommand> {

    @Valid @NotNull
    private OrderId orderId;

    public CompleteOrderCommand(OrderId orderId) {
        this.orderId = orderId;
        validateSelf();
    }
}
