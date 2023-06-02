package com.ys.order.application.port.in;

import com.ys.order.common.SelfValidating;
import com.ys.order.domain.core.OrderLines;
import com.ys.order.domain.core.Orderer;
import com.ys.order.domain.core.PaymentInfos;
import com.ys.order.domain.core.ShippingInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PreOrderCommand extends SelfValidating<PreOrderCommand> {

    @Valid @NotNull
    Orderer orderer;
    @Valid @NotNull
    OrderLines orderLines;
    @Valid @NotNull
    PaymentInfos paymentInfos;
    @Valid @NotNull
    ShippingInfo shippingInfo;

    public PreOrderCommand(
            Orderer orderer,
            OrderLines orderLines,
            PaymentInfos paymentInfos,
            ShippingInfo shippingInfo
    ) {
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.paymentInfos = paymentInfos;
        this.shippingInfo = shippingInfo;
        validateSelf();
    }
}
