package com.bingbong.defguidespringbatch.chapter7.copyfile.domain;

public class Customer {
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String street;
	private String addressNumber;
	private String city;
	private String state;
	private String zipCode;
	
	public Customer() {
	}
	
	public Customer(String firstName, String middleInitial, String lastName, String street, String addressNumber, String city, String state, String zipCode) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.street = street;
		this.addressNumber = addressNumber;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
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
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getAddressNumber() {
		return addressNumber;
	}
	
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
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
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return "Customer{" +
				"firstName='" + firstName + '\'' +
				", middleInitial='" + middleInitial + '\'' +
				", lastName='" + lastName + '\'' +
				", street='" + street + '\'' +
				", addressNumber='" + addressNumber + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zipCode='" + zipCode + '\'' +
				'}';
	}
}
