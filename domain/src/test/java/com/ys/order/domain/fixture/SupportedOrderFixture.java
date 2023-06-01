package com.ys.order.domain.fixture;

import com.refs.productoption.domain.ProductOptionId;
import com.ys.order.domain.core.*;
import com.ys.refs.user.domain.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static com.ys.order.domain.core.OrderStatus.*;
import static com.ys.order.domain.core.PaymentMethod.*;

public class SupportedOrderFixture {

    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final OrderId ORDER_ID = OrderId.of("ORDER_ID");
    protected static final UserId USER_ID = UserId.of("USER_ID");
    protected static final Orderer ORDERER = Orderer.of(USER_ID, "tester", "010-7777-7777");
    
    protected static final ProductOptionId PRODUCT_OPTION_ID = ProductOptionId.of("PRODUCT_OPTION_ID");
    protected static final ProductOptionId PRODUCT_OPTION_ID_2 = ProductOptionId.of("PRODUCT_OPTION_ID_2");
    protected static final ProductOptionId PRODUCT_OPTION_ID_3 = ProductOptionId.of("PRODUCT_OPTION_ID_3");
    protected static final OrderLine ORDER_LINE = OrderLine.of(
            PRODUCT_OPTION_ID, Money.of(1000), 2);
    protected static final OrderLine ORDER_LINE_2 = OrderLine.of(
            PRODUCT_OPTION_ID_2, Money.of(1700), 1);
    protected static final OrderLine ORDER_LINE_3 = OrderLine.of(
            PRODUCT_OPTION_ID_3, Money.of(2500), 3);
    protected static final OrderLines ORDER_LINES = OrderLines.of(new ArrayList<>(Arrays.asList(
            ORDER_LINE, ORDER_LINE_2, ORDER_LINE_3
    )));
    protected static final PaymentInfo CARD_PAYMENT_INFO = PaymentInfo.of(CARD, Money.of(23000));
    protected static final PaymentInfo POINT_PAYMENT_INFO = PaymentInfo.of(POINT, Money.of(23000));
    protected static final PaymentInfo COUPON_PAYMENT_INFO = PaymentInfo.of(COUPON, Money.of(23000));
    protected static final PaymentInfos PAYMENT_INFOS = PaymentInfos.of(new ArrayList<>(Arrays.asList(
            CARD_PAYMENT_INFO, POINT_PAYMENT_INFO, COUPON_PAYMENT_INFO
    )));

    protected static final Receiver RECEIVER = Receiver.of("RECEIVER", "010-8888-8888");
    protected static final Address ADDRESS = Address.of("23456", "서울 강남구 강남대로 007", "502호");
    protected static final String MESSAGE = "ANY_MESSAGE";
    protected static final ShippingInfo SHIPPING_INFO = ShippingInfo.of(RECEIVER, ADDRESS, MESSAGE);

    protected static final Order PRE_ORDER = Order.of(
            ORDER_ID, ORDERER, PAYMENT_WAITING, ORDER_LINES.calculateTotalAmount(), ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO, NOW, NOW, 0L
    );
    protected static final Order COMPLETED_ORDER = Order.of(
            ORDER_ID, ORDERER, COMPLETED, ORDER_LINES.calculateTotalAmount(), ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO, NOW, NOW, 1L
    );
    protected static final Order PREPARING_ORDER = Order.of(
            ORDER_ID, ORDERER, PREPARING, ORDER_LINES.calculateTotalAmount(), ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO, NOW, NOW, 2L
    );
    protected static final Order SHIPPED_ORDER = Order.of(
            ORDER_ID, ORDERER, SHIPPED, ORDER_LINES.calculateTotalAmount(), ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO, NOW, NOW, 3L
    );
    protected static final Order DELIVERING_ORDER = Order.of(
            ORDER_ID, ORDERER, DELIVERING, ORDER_LINES.calculateTotalAmount(), ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO, NOW, NOW, 4L
    );

}
