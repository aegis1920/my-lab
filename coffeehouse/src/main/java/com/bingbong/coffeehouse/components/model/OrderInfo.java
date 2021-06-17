package com.bingbong.coffeehouse.components.model;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class OrderInfo implements Serializable {
	
	private UUID orderId;
	private String coffee;
	
	@Builder
	public OrderInfo(UUID orderId, String coffee) {
		this.orderId = orderId;
		this.coffee = coffee;
	}
}
