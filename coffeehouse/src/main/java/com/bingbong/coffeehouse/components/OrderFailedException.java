package com.bingbong.coffeehouse.components;

public class OrderFailedException extends RuntimeException {
	public OrderFailedException(Exception error) {
		super(error);
	}
}
