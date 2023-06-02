package com.ys.order.adapter.out.persistence.fixture;

import com.ys.order.adapter.out.persistence.OrderEntity;
import com.ys.order.adapter.out.persistence.OrderLineCollection;
import com.ys.order.adapter.out.persistence.OrderPaymentInfoCollection;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.ys.order.domain.core.OrderStatus.PAYMENT_WAITING;
import static com.ys.order.domain.core.PaymentMethod.CARD;
import static com.ys.order.domain.core.PaymentMethod.POINT;

public class SupportOrderEntityFixture {

    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final String ANY_ORDER_ID = "ANY_ORDER_ID";
    protected static final String ANY_ORDERER_USER_ID = "ANY_ORDERER_USER_ID";
    protected static final String ANY_ORDERER_NAME = "ANY_ORDERER_NAME";
    protected static final String ANY_ORDERER_PHONE = "010-7777-7777";
    protected static final String ANY_RECEIVER_NAME = "ANY_RECEIVER_NAME";
    protected static final String ANY_RECEIVER_PHONE = "010-8888-8888";
    protected static final String ANY_SHIPPING_ZIPCODE = "00700";
    protected static final String ANY_SHIPPING_FIRST_LINE = "ANY_SHIPPING_FIRST_LINE";
    protected static final String ANY_SHIPPING_SECOND_LINE = "ANY_SHIPPING_SECOND_LINE";
    protected static final String ANY_SHIPPING_MESSAGE = "ANY_SHIPPING_MESSAGE";
    protected static final String ANY_PRODUCT_OPTION_ID = "ANY_PRODUCT_OPTION_ID";
    protected static final String ANY_PRODUCT_OPTION_ID_2 = "ANY_PRODUCT_OPTION_ID_2";
    protected static final Integer ANY_TOTAL_AMOUNT = 7200;
    protected static final OrderLineCollection ORDER_LINE_COLLECTION = new OrderLineCollection(
            ANY_PRODUCT_OPTION_ID, 1000, 3
    );
    protected static final OrderLineCollection ORDER_LINE_COLLECTION_2 = new OrderLineCollection(
            ANY_PRODUCT_OPTION_ID_2, 2100, 2
    );
    protected static final OrderPaymentInfoCollection ORDER_PAYMENT_INFO_COLLECTION = new OrderPaymentInfoCollection(
            CARD, 7000
    );
    protected static final OrderPaymentInfoCollection ORDER_PAYMENT_INFO_COLLECTION_2 = new OrderPaymentInfoCollection(
            POINT, 200
    );
    protected static final OrderEntity ORDER_ENTITY = new OrderEntity(
            ANY_ORDER_ID, ANY_ORDERER_USER_ID, ANY_ORDERER_NAME, ANY_ORDERER_PHONE, PAYMENT_WAITING,
            ANY_TOTAL_AMOUNT, Arrays.asList(ORDER_LINE_COLLECTION, ORDER_LINE_COLLECTION_2),
            Arrays.asList(ORDER_PAYMENT_INFO_COLLECTION, ORDER_PAYMENT_INFO_COLLECTION_2),
            ANY_RECEIVER_NAME, ANY_RECEIVER_PHONE, ANY_SHIPPING_ZIPCODE, ANY_SHIPPING_FIRST_LINE, ANY_SHIPPING_SECOND_LINE, ANY_SHIPPING_MESSAGE,
            null, null, null
    );
}
