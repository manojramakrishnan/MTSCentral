package com.multiplicandin.mts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//import com.innovationstack.ayurmed.model.CustomerOrder;

@Entity
public class OrderItem {

    public OrderItem(Integer id, Double price, CustomerOrder customerOrder,
			boolean include, boolean submitted) {
		super();
		this.id = id;
		this.price = price;
		this.customerOrder = customerOrder;
		this.include = include;
		this.submitted = submitted;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", price=" + price + ", customerOrder=" + customerOrder + ", include=" + include + ", submitted=" + submitted
				+ "]";
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder userOrder) {
		this.customerOrder = userOrder;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		return price;
	}
	
	public OrderItem() {
		
	}
	

	private Double price;

    @ManyToOne
    private CustomerOrder customerOrder;

    @Column(name = "include", nullable = false)
    private boolean include = false;

    @Column(name = "submitted", nullable = false)
    private boolean submitted = false;

}
