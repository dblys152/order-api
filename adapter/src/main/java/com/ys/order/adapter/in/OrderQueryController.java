package com.ys.order.adapter.in;

import com.ys.order.adapter.in.model.ApiResponse;
import com.ys.order.adapter.in.model.OrderModel;
import com.ys.order.application.port.in.GetOrderQuery;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import com.ys.order.domain.core.Orders;
import com.ys.refs.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequestMapping(value = "/v1/orders",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class OrderQueryController {

    private final GetOrderQuery getOrderQuery;

    @GetMapping("")
    public ResponseEntity getOrders(
            @RequestParam("userId") String userId) {

        Orders orders = getOrderQuery.getOrders(UserId.of(userId));

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(orders.getItems().stream()
                        .map(OrderModel::fromDomain)
                        .collect(Collectors.toList())));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getOrder(
            @PathVariable("orderId") String orderId) {

        Order order = getOrderQuery.getOrder(OrderId.of(orderId));

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(OrderModel.fromDomain(order)));
    }
}
