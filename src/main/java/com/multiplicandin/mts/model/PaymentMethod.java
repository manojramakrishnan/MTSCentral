package com.multiplicandin.mts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cardOwner")
    private String cardOwner;

    @Column(name = "expirationMonth")
    private Integer expirationMonth;

    @Column(name = "expirationYear")
    private Integer expirationYear;

    @Column(name = "creditCardNumber")
    private String creditCardNumber;

    @Column(name = "cardSecurityCode")
    private Integer cardSecurityCode;

    @ManyToOne
    @JoinColumn
    private Customer customer;
    
    public PaymentMethod() {}

	public PaymentMethod(Integer id, String cardOwner, Integer expirationMonth, Integer expirationYear,
			String creditCardNumber, Integer cardSecurityCode, Customer customer) {
		super();
		this.id = id;
		this.cardOwner = cardOwner;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.creditCardNumber = creditCardNumber;
		this.cardSecurityCode = cardSecurityCode;
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "PaymentMethod [id=" + id + ", cardOwner=" + cardOwner + ", expirationMonth=" + expirationMonth
				+ ", expirationYear=" + expirationYear + ", creditCardNumber=" + creditCardNumber
				+ ", cardSecurityCode=" + cardSecurityCode + ", customer=" + customer + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public Integer getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Integer getCardSecurityCode() {
		return cardSecurityCode;
	}

	public void setCardSecurityCode(Integer cardSecurityCode) {
		this.cardSecurityCode = cardSecurityCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    
    

}
