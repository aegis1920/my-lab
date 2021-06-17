package com.bingbong.coffeehouse.apps.customer;

import com.bingbong.coffeehouse.components.OrderFailedException;
import com.bingbong.coffeehouse.components.model.OrderPlaced;
import com.bingbong.coffeehouse.components.model.OrderInfo;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.*;
import java.rmi.RemoteException;
import java.util.UUID;

public class OrderCounterEJB implements SessionBean {
	
	// 글래시 피시라는 컨테이너
	private SessionContext ctx;
	
	// jms(자바 메시지 시스템)을 쓰기 위해 필요한 두 개의 필드
	private ConnectionFactory connectionFactory;
	private Queue queue;
	
	public OrderInfo placeOrder(String coffee) {
		OrderInfo orderInfo = new OrderInfo(UUID.randomUUID(), coffee);
		OrderPlaced orderPlaced = OrderPlaced.of(orderInfo);
		
		// 주문 정보를 검증하고, 저장하는
		
		try (Connection connection = connectionFactory.createConnection()){
			try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
				ObjectMessage message = session.createObjectMessage(orderPlaced);
				session.createProducer(queue).send(message);
			}
		
		} catch (JMSException error) {
			throw new OrderFailedException(error);
		}
		
		return orderInfo;
	}
	
	@Override
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		this.ctx = ctx;
	}
	
	public void ejbCreate() throws CreateException {
		// Java EE 서버로부터 가져옴
		// 두 개의 컴포넌트를 가져옴
		connectionFactory = (ConnectionFactory) ctx.lookup("java:comp/DefaultJMSConnectionFactory");
		queue = (Queue) ctx.lookup("jms.orderQueue");
	}
	
	@Override
	public void ejbRemove() throws EJBException, RemoteException {
	
	}
	
	@Override
	public void ejbActivate() throws EJBException, RemoteException {
	
	}
	
	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
	
	}
}
