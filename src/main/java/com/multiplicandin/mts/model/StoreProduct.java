package com.multiplicandin.mts.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class StoreProduct {

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public StoreProduct() {
		
	}

	public StoreProduct(int id, int quantity, double price, Product product, Store store,
			String productId) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.product = product;
		this.store = store;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "StoreProduct [id=" + id + ", quantity=" + quantity + ", price=" + price + ", product=" + product + ", store=" + store + ", productId=" + productId + "]";
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Store getPharmacy() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
   // @NotEmpty(message = "*Can't be blank")
    private int quantity;

    @Column(name = "price")
   // @NotEmpty(message = "*Can't be blank")
    private double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;

    @Transient
    private String productId;
}
