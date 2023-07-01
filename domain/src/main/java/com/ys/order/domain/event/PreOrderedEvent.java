package com.ys.order.domain.event;

import com.ys.order.domain.core.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreOrderedEvent {

    @NotNull
    String orderId;

    public static PreOrderedEvent fromDomain(Order order) {
        return new PreOrderedEvent(order.getId().getId());
    }
}
