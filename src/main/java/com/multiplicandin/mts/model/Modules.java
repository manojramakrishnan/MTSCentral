package com.multiplicandin.mts.model;

import java.util.ArrayList;
import java.util.List;

public class Modules {

	private List<Customer> customer= new ArrayList<>();
	

	@Override
	public String toString() {
		return "Modules [customer=" + customer + "]";
	}


	public Modules(List<Customer> customer) {
		super();
		this.customer = customer;
	}


	public List<Customer> getCustomer() {
		return customer;
	}


	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}


	public Modules() {
		
	}
	
}
