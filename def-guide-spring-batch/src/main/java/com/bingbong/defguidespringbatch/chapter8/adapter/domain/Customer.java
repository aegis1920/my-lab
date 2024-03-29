package com.bingbong.defguidespringbatch.chapter8.adapter.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Customer {
	
	@NotNull(message="First name is required")
	@Pattern(regexp="[a-zA-Z]+", message="First name must be alphabetical")
	private String firstName;
	
	/**
	 * @Pattern에 @Size를 포함시킬 수 있는데 함께 적용한 이유는
	 * 길이가 잘못됐는지, 형식이 잘못됐는지 알 수 있어서
	 */
	@Size(min=1, max=1)
	@Pattern(regexp="[a-zA-Z]", message="Middle initial must be alphabetical")
	private String middleInitial;
	
	@NotNull(message="Last name is required")
	@Pattern(regexp="[a-zA-Z]+", message="Last name must be alphabetical")
	private String lastName;
	
	@NotNull(message="Address is required")
	@Pattern(regexp="[0-9a-zA-Z\\. ]+")
	private String address;
	
	@NotNull(message="City is required")
	@Pattern(regexp="[a-zA-Z\\. ]+")
	private String city;
	
	@NotNull(message="State is required")
	@Size(min=2,max=2)
	@Pattern(regexp="[A-Z]{2}")
	private String state;
	
	@NotNull(message="Zip is required")
	@Size(min=5,max=5)
	@Pattern(regexp="\\d{5}")
	private String zip;
	
	public Customer() {}

	public Customer(Customer customer) {
		this.firstName = customer.getFirstName();
		this.middleInitial = customer.getMiddleInitial();
		this.lastName = customer.getLastName();
		this.address = customer.getAddress();
		this.city = customer.getCity();
		this.state = customer.getState();
		this.zip = customer.getZip();
	}
	
	public Customer(String firstName, String middleInitial, String lastName, String address, String city, String state, String zip) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleInitial() {
		return middleInitial;
	}
	
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Override
	public String toString() {
		return "Customer{" +
				"firstName='" + firstName + '\'' +
				", middleInitial='" + middleInitial + '\'' +
				", lastName='" + lastName + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zip='" + zip + '\'' +
				'}';
	}
}
