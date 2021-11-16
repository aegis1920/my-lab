package com.bingbong.defguidespringbatch.chapter13.validator;

public class CustomerUpdate {
	private final long id;
	
	public CustomerUpdate(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "CustomerUpdate{" +
				"id=" + id +
				'}';
	}
}
