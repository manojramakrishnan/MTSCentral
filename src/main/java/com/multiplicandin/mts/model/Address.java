package com.multiplicandin.mts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Integer id;
	
	@Column(name="addresslineone")
	private String addressLineOne;

	@Column(name="addresslinetwo")
	private String addressLineTwo; 
	
	@Column(name="district")
	private String district;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="customerid")
	private int customerId;
		

		public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getAddressLineOne() {
		return addressLineOne;
	}


	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}


	public String getAddressLineTwo() {
		return addressLineTwo;
	}


	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


		@Override
	public String toString() {
		return "Address [id=" + id + ", addressLineOne=" + addressLineOne + ", addressLineTwo=" + addressLineTwo
				+ ", district=" + district + ", state=" + state + ", country=" + country + ", mobile=" + mobile
				+ ", customerId=" + customerId + "]";
	}


		public Address(Integer id, String addressLineOne, String addressLineTwo, String district, String state,
			String country, String mobile, int customerId) {
		super();
		this.id = id;
		this.addressLineOne = addressLineOne;
		this.addressLineTwo = addressLineTwo;
		this.district = district;
		this.state = state;
		this.country = country;
		this.mobile = mobile;
		this.customerId = customerId;
	}


		public Address() {
		
	}
	
	
	}
