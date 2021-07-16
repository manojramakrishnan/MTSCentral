package com.multiplicandin.mts.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CustomerOrder {

    public java.util.Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(java.util.Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getShippingDate() {
		return shippingDate;
	}
	
	public CustomerOrder() {
		
	}

	public CustomerOrder(Integer id, boolean submitted, Date orderDate, String orderStatus, BigDecimal orderTotal,
			Date shippingDate, Customer customer, Store store, List<OrderItem> orderItems, boolean active) {
		super();
		this.id = id;
		this.submitted = submitted;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
		this.shippingDate = shippingDate;
		this.customer = customer;
		this.store = store;
		this.orderItems = orderItems;
		this.active = active;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    private boolean submitted; // yes or no

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date orderDate;

    private String orderStatus;

    private BigDecimal orderTotal;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shippingDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Store store;

    // Fetch Lazy, because of reference an unknown target entity property
    // Ref: https://stackoverflow.com/questions/4011472/mappedby-reference-an-unknown-target-entity-property
    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Column(name = "active", nullable = false)
    private boolean active = false;
}
