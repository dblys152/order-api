package com.ys.order.domain.event;

import com.ys.order.domain.core.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCompletedEvent {

    @NotNull
    String orderId;

    public static OrderCompletedEvent fromDomain(Order order) {
        return new OrderCompletedEvent(order.getId().getId());
    }
}
