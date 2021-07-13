package com.multiplicandin.mts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String content;

    private Integer userOrderId;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date alertDate;

    @Column(name = "reading", nullable = false)
    private boolean reading = false;

    @ManyToOne
    private Customer customer;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserOrderId() {
		return userOrderId;
	}

	public void setUserOrderId(Integer userOrderId) {
		this.userOrderId = userOrderId;
	}

	public java.util.Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(java.util.Date alertDate) {
		this.alertDate = alertDate;
	}

	public Boolean isReading() {
		return reading;
	}

	public void setReading(Boolean reading) {
		this.reading = reading;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
}
