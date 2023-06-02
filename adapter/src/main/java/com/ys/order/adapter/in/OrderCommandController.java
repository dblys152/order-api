package com.ys.order.adapter.in;

import com.refs.productoption.domain.ProductOptionId;
import com.ys.order.adapter.in.model.ApiResponse;
import com.ys.order.adapter.in.model.OrderModel;
import com.ys.order.adapter.in.model.PreOrderRequest;
import com.ys.order.application.port.in.CompleteOrderCommand;
import com.ys.order.application.port.in.CompleteOrderUseCase;
import com.ys.order.application.port.in.PreOrderCommand;
import com.ys.order.application.port.in.PreOrderUseCase;
import com.ys.order.domain.core.*;
import com.ys.refs.user.domain.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = "/v1/orders",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class OrderCommandController {

    private final PreOrderUseCase preOrderUseCase;
    private final CompleteOrderUseCase completeOrderUseCase;

    @PostMapping("")
    public ResponseEntity preOrder(
            @RequestBody @Valid PreOrderRequest request) {

        Order preOrdered = preOrderUseCase.preOrder(createPreOrderCommand(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(OrderModel.fromDomain(preOrdered)));
    }

    @PatchMapping("/{orderId}/complete")
    public ResponseEntity completeOrder(
            @PathVariable("orderId") String orderId) {

        Order ordered = completeOrderUseCase.complete(new CompleteOrderCommand(
                OrderId.of(orderId)));

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(OrderModel.fromDomain(ordered)));
    }

    private PreOrderCommand createPreOrderCommand(PreOrderRequest request) {
        PreOrderRequest.Orderer orderer = request.getOrderer();
        List<PreOrderRequest.OrderLineRequest> orderLineList = request.getOrderLines();
        List<PreOrderRequest.PaymentInfoRequest> paymentInfoList = request.getPaymentInfos();
        PreOrderRequest.ShippingInfoRequest shippingInfo = request.getShippingInfo();

        return new PreOrderCommand(
                Orderer.of(UserId.of(orderer.getUserId()), orderer.getName(), orderer.getPhone()),
                OrderLines.of(orderLineList.stream()
                        .map(ol -> OrderLine.of(
                                ProductOptionId.of(ol.getProductOptionId()),
                                Money.of(ol.getPrice()),
                                ol.getQuantity()
                        ))
                        .collect(Collectors.toList())
                ),
                PaymentInfos.of(paymentInfoList.stream()
                        .map(p -> PaymentInfo.of(p.getMethod(), Money.of(p.getAmount())))
                        .collect(Collectors.toList())
                ),
                ShippingInfo.of(
                        Receiver.of(shippingInfo.getReceiverName(), shippingInfo.getReceiverPhone()),
                        Address.of(
                                shippingInfo.getZipcode(), shippingInfo.getFirstLine(), shippingInfo.getSecondLine()
                        ),
                        shippingInfo.getMessage()
                )
        );
    }
}
