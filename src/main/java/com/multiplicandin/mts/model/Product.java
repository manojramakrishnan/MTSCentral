
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

    public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<StoreProduct> getProducts() {
		return products;
	}
	
	public Product() {}
	
	public Product(int id, String product_name, String product_code,
			@NotEmpty(message = "*Can't be blank") String category, int quantity, Set<StoreProduct> products) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.product_code = product_code;
		this.category = category;
		this.quantity = quantity;
		this.products = products;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", product_name=" + product_name + ", product_code=" + product_code + ", category="
				+ category + ", quantity=" + quantity + ", products=" + products + "]";
	}

	public void setProducts(Set<StoreProduct> products) {
		this.products = products;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String product_name;

    private String product_code; // Ministry of Health Medicine Code

    @Column(name = "category")
    @NotEmpty(message = "*Can't be blank")
    private String category;
    
    @Column(name = "quantity")
    // @NotEmpty(message = "*Can't be blank")
     private int quantity=0;

    @OneToMany(mappedBy = "product")
    Set<StoreProduct> products;
}
