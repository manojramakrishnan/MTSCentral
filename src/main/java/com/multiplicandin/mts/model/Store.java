
package com.multiplicandin.mts.model;



import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Store {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    private String store_name;

    @Column(name = "active", nullable = true)
    private boolean active;

    @OneToMany(mappedBy = "store")
    Set<StoreProduct> storeProducts;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Customer> customers;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;
	
    

	
	public Store() {
		
	}

	public Store(int id, String store_name, boolean active, Set<StoreProduct> storeProducts, List<Customer> customers,
			List<CustomerOrder> customerOrders) {
		super();
		this.id = id;
		this.store_name = store_name;
		this.active = active;
		this.storeProducts = storeProducts;
		this.customers = customers;
		this.customerOrders = customerOrders;
	}
	
	@Override
	public String toString() {
		return "Store [id=" + id + ", store_name=" + store_name + ", active=" + active + ", storeProducts="
				+ storeProducts + ", customers=" + customers + ", customerOrders=" + customerOrders + "]";
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<StoreProduct> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(Set<StoreProduct> storeProducts) {
		this.storeProducts = storeProducts;
	}
	
	

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<CustomerOrder> getCustomerOrders() {
		return customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	


}

