package com.bingbong.coffeehouse.apps.customer;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

// EJB 컴포넌트는 로컬 인터페이스, 를 생성할 수 있는 Home 인터페이스를 함께 제공해줘야 한다.
public interface OrderCounterLocalHome extends EJBLocalHome {
	
	OrderCounterLocal create() throws CreateException;
}
