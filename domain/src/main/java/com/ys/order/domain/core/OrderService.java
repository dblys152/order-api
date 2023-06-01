package com.ys.order.domain.core;

public interface OrderService {

    void complete();
    void cancel();
    void changeShippingInfo(Receiver receiver, Address address, String message);
}
