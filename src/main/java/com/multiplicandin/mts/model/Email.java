package com.multiplicandin.mts.model;


public class Email {

	private String deliveryEmailId;
	
	private String message;
	
	public Email() {
		
	}

	public Email(String deliveryEmailId, String message) {
		super();
		this.deliveryEmailId = deliveryEmailId;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Email [deliveryEmailId=" + deliveryEmailId + ", message=" + message + "]";
	}

	public String getDeliveryEmailId() {
		return deliveryEmailId;
	}

	public void setDeliveryEmailId(String deliveryEmailId) {
		this.deliveryEmailId = deliveryEmailId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
