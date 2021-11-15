package com.multiplicandin.mts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Delivery {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private int id;

	@Column(name="orderId")
	private int orderId;
	
	@Column(name="customerId")
	private int customerId;

	@Column(name="customerName")
	private String customerName;
	
	@Column(name="orderAddress")
	private String orderAddress;
	
	@Column(name="deliveryStatus")
	private String deliveryStatus;
	
	@Column(name="deliveryGuyName")
	private String deliveryGuyName;
	
	public Delivery() {
		}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryGuyName() {
		return deliveryGuyName;
	}

	public void setDeliveryGuyName(String deliveryGuyName) {
		this.deliveryGuyName = deliveryGuyName;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + id + ", orderId=" + orderId + ", customerId=" + customerId + ", customerName="
				+ customerName + ", orderAddress=" + orderAddress + ", deliveryStatus=" + deliveryStatus
				+ ", deliveryGuyName=" + deliveryGuyName + "]";
	}

	public Delivery(int id, int orderId, int customerId, String customerName, String orderAddress,
			String deliveryStatus, String deliveryGuyName) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.orderAddress = orderAddress;
		this.deliveryStatus = deliveryStatus;
		this.deliveryGuyName = deliveryGuyName;
	}

		
}
