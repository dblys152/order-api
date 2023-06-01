package com.ys.order.domain.core;

public interface OrderService {

    void complete();
    void changeShippingInfo(Receiver receiver, Address address, String message);
    void cancel();
}
