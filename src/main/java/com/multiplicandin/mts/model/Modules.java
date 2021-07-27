package com.multiplicandin.mts.model;

import java.util.ArrayList;
import java.util.List;

public class Modules {

	private List<Customer> customer= new ArrayList<>();
	private List<StoreProduct> storeProduct = new ArrayList<>(); 
	private List<CustomerOrder> customerOrder= new ArrayList<>();
	private List<PaymentMethod> paymentMethod= new ArrayList<>();
	private List<Product> product= new ArrayList<>();
	private List<Estimate> estimate= new ArrayList<>();

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public List<StoreProduct> getStoreProduct() {
		return storeProduct;
	}

	public void setStoreProduct(List<StoreProduct> storeProduct) {
		this.storeProduct = storeProduct;
	}

	public List<CustomerOrder> getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(List<CustomerOrder> customerOrder) {
		this.customerOrder = customerOrder;
	}

	public List<PaymentMethod> getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(List<PaymentMethod> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public List<Estimate> getEstimate() {
		return estimate;
	}

	public void setEstimate(List<Estimate> estimate) {
		this.estimate = estimate;
	}

	public Modules(List<Customer> customer, List<StoreProduct> storeProduct, List<CustomerOrder> customerOrder,
			List<PaymentMethod> paymentMethod, List<Product> product, List<Estimate> estimate) {
		super();
		this.customer = customer;
		this.storeProduct = storeProduct;
		this.customerOrder = customerOrder;
		this.paymentMethod = paymentMethod;
		this.product = product;
		this.estimate = estimate;
	}

	@Override
	public String toString() {
		return "Modules [customer=" + customer + ", storeProduct=" + storeProduct + ", customerOrder=" + customerOrder
				+ ", paymentMethod=" + paymentMethod + ", product=" + product + ", estimate=" + estimate + "]";
	}

	public Modules() {
		
	}




	
}
