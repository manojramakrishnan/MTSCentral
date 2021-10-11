package com.multiplicandin.mts.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("paytm.payment.sandbox")
public class PaymentDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "merchantId")
	private String merchantId;
	
    @Column(name = "merchantKey")
	private String merchantKey;
	
    @Column(name = "channelId")
	private String channelId;
	
    @Column(name = "website")
	private String website;
	
    @Column(name = "industryTypeId")
	private String industryTypeId;
	
    @Column(name = "paytmUrl")
	private String paytmUrl;
	
    @Column(name = "details")
	private Map<String, String> details;
	
    @Column(name = "total")
	private double total;
	

    
    @Column(name = "customer_id")
    private int customerId;


    @Column(name = "order_id")
    private int orderId;
	
    public PaymentDetails() {
		
	}

	public PaymentDetails(int id, String merchantId, String merchantKey, String channelId, String website,
			String industryTypeId, String paytmUrl, Map<String, String> details, double total, int customerId,
			int orderId) {
		super();
		this.id = id;
		this.merchantId = merchantId;
		this.merchantKey = merchantKey;
		this.channelId = channelId;
		this.website = website;
		this.industryTypeId = industryTypeId;
		this.paytmUrl = paytmUrl;
		this.details = details;
		this.total = total;
		this.customerId = customerId;
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "PaymentDetails [id=" + id + ", merchantId=" + merchantId + ", merchantKey=" + merchantKey
				+ ", channelId=" + channelId + ", website=" + website + ", industryTypeId=" + industryTypeId
				+ ", paytmUrl=" + paytmUrl + ", details=" + details + ", total=" + total + ", customerId=" + customerId
				+ ", orderId=" + orderId + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getIndustryTypeId() {
		return industryTypeId;
	}

	public void setIndustryTypeId(String industryTypeId) {
		this.industryTypeId = industryTypeId;
	}

	public String getPaytmUrl() {
		return paytmUrl;
	}

	public void setPaytmUrl(String paytmUrl) {
		this.paytmUrl = paytmUrl;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	
}
