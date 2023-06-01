package com.ys.order.domain.core;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class PaymentInfos {

    @Size(min = 1)
    List<PaymentInfo> items;
}
