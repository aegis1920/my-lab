package com.bingbong.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {
	
	private String url;
	
	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void connect() {
		System.out.println("connect : " + url);
	}
	
	public void call(String message) {
		System.out.println("call : " + url + " message : " + message);
	}
	
	public void disconnect() {
		System.out.println("close : " + url);
	}
	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		connect();
//		call("초기화 연결 메시지");
//	}
//
//	@Override
//	public void destroy() throws Exception {
//		disconnect();
//	}
	
	@PostConstruct
	public void init() throws Exception {
		connect();
		call("초기화 연결 메시지");
	}

	@PreDestroy
	public void close() throws Exception {
		disconnect();
	}
	
	
}
