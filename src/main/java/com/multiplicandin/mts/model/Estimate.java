package com.multiplicandin.mts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Estimate {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    @Column(name = "brand")
	private String brand;
    
    @Column(name = "size")
	private int size;
    
    @Column(name = "size_Unit")
    private int sizeUnit;
    
    @Column(name = "total")
    private int total;
	
    @Column(name = "price")
    private int price;
    
    @Column(name = "delivery_Point")
	private String deliveryPoint;
    
    @Column(name = "place")
	private String place;
    
    @Column(name = "contact_Number")
	private int contactNumber;
    
    
	@Column(name = "employee_id")
	private String employee;
	
    
   	@Column(name = "address_id")
   	private String address;
   	
    @ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSizeUnit() {
		return sizeUnit;
	}

	public void setSizeUnit(int sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Estimate [id=" + id + ", brand=" + brand + ", size=" + size + ", sizeUnit=" + sizeUnit + ", total="
				+ total + ", price=" + price + ", deliveryPoint=" + deliveryPoint + ", place=" + place
				+ ", contactNumber=" + contactNumber + ", employee=" + employee + ", address=" + address + ", store="
				+ store + ", customer=" + customer + ", product=" + product + "]";
	}
	public Estimate() {}

	public Estimate(int id, String brand, int size, int sizeUnit, int total, int price, String deliveryPoint,
			String place, int contactNumber, String employee, String address, Store store, Customer customer,
			Product product) {
		super();
		this.id = id;
		this.brand = brand;
		this.size = size;
		this.sizeUnit = sizeUnit;
		this.total = total;
		this.price = price;
		this.deliveryPoint = deliveryPoint;
		this.place = place;
		this.contactNumber = contactNumber;
		this.employee = employee;
		this.address = address;
		this.store = store;
		this.customer = customer;
		this.product = product;
	}


}
