package com.ys.order.adapter.out.persistence;

import com.ys.order.domain.core.*;
import com.ys.refs.user.domain.UserId;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Slf4j
@ToString
public class OrderEntity {

    @Id
    String id;

    @Column(name = "orderer_id", nullable = false)
    String ordererId;
    @Column(name = "orderer_name", nullable = false)
    String ordererName;
    @Column(name = "orderer_phone", nullable = false)
    String ordererPhone;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    Integer totalAmount;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_lines",
            joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "idx")
    List<OrderLineCollection> orderLineCollectionList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_payment_infos",
            joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "idx")
    List<OrderPaymentInfoCollection> orderPaymentInfoCollectionList;

    @Column(name = "receiver_name", nullable = false)
    String receiverName;
    @Column(name = "receiver_phone", nullable = false)
    String receiverPhone;
    @Column(name = "shipping_zipcode", nullable = false)
    String shipping_zipcode;
    @Column(name = "shipping_first_line", nullable = false)
    String shippingFirstLine;
    @Column(name = "shipping_second_line", nullable = false)
    String shippingSecondLine;
    @Column(name = "shipping_message")
    String shippingMessage;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;
    @Version
    private Long version;

    public static OrderEntity fromDomain(Order order) {
        OrderId orderId = order.getId();
        Orderer orderer = order.getOrderer();
        OrderLines orderLines = order.getOrderLines();
        PaymentInfos paymentInfos = order.getPaymentInfos();
        ShippingInfo shippingInfo = order.getShippingInfo();
        Receiver receiver = shippingInfo.getReceiver();
        Address address = shippingInfo.getAddress();
        return new OrderEntity(
                orderId.getId(),
                orderer.getUserId().getId(),
                orderer.getName(),
                orderer.getPhone(),
                order.getStatus(),
                order.getTotalAmount().getValue(),
                orderLines.getItems().stream()
                        .map(OrderLineCollection::fromDomain)
                        .collect(Collectors.toList()),
                paymentInfos.getItems().stream()
                        .map(OrderPaymentInfoCollection::fromDomain)
                        .collect(Collectors.toList()),
                receiver.getName(),
                receiver.getPhone(),
                address.getZipcode(),
                address.getFirstLine(),
                address.getSecondLine(),
                shippingInfo.getMessage(),
                order.getCreated_at(),
                order.getModified_at(),
                order.getVersion()
        );
    }

    public Order toDomain() {
        return Order.of(
                OrderId.of(this.id),
                Orderer.of(UserId.of(this.ordererId), this.ordererName, this.ordererPhone),
                this.status,
                Money.of(this.totalAmount),
                OrderLines.of(this.orderLineCollectionList.stream()
                        .map(ol -> ol.toDomain())
                        .collect(Collectors.toList())),
                PaymentInfos.of(this.orderPaymentInfoCollectionList.stream()
                        .map(p -> p.toDomain())
                        .collect(Collectors.toList())),
                ShippingInfo.of(
                        Receiver.of(this.receiverName, this.receiverPhone),
                        Address.of(this.shipping_zipcode, this.shippingFirstLine, this.shippingSecondLine),
                        this.shippingMessage
                ),
                this.createdAt,
                this.modifiedAt,
                this.version
        );
    }
}
