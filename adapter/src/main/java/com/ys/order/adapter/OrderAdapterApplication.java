package com.ys.order.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ys.order")
public class OrderAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderAdapterApplication.class, args);
	}

}
