package com.bingbong.coffeehouse.apps.customer;

import com.bingbong.coffeehouse.components.model.OrderInfo;

import javax.ejb.EJBLocalObject;

public interface OrderCounterLocal extends EJBLocalObject {
	OrderInfo placeOrder(String coffee);
}
