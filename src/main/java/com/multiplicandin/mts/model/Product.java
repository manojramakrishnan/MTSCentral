package com.multiplicandin.mts.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Product {

	


	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productName;

    private String productCode; // Ministry of Health Medicine Code

    @Column(name = "category")
    @NotEmpty(message = "*Can't be blank")
    private String category;
    
    @Column(name = "quantity")
    // @NotEmpty(message = "*Can't be blank")
     private int quantity=0;

    @OneToMany(mappedBy = "product")
    Set<StoreProduct> products;

    public Product(int id, String productName, String productCode,
			@NotEmpty(message = "*Can't be blank") String category, int quantity, Set<StoreProduct> products) {
		super();
		this.id = id;
		this.productName = productName;
		this.productCode = productCode;
		this.category = category;
		this.quantity = quantity;
		this.products = products;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productCode=" + productCode + ", category="
				+ category + ", quantity=" + quantity + ", products=" + products + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Set<StoreProduct> getProducts() {
		return products;
	}

	public void setProducts(Set<StoreProduct> products) {
		this.products = products;
	}

	public Product() {}

}

