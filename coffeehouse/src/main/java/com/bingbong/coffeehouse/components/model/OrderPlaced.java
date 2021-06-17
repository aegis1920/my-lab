package com.bingbong.coffeehouse.components.model;

import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class OrderPlaced implements Serializable {
	
	private OrderInfo orderInfo;
}
