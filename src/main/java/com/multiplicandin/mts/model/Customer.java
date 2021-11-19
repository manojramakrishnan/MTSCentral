package com.multiplicandin.mts.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;




@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private int id;
	
	// @Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Can't be blank")
	private String email;

	private String contactNo;
	
	@ManyToOne
	@JoinColumn
	private Role role;
	
	@ManyToMany()
	@JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

	@ManyToOne
	@JoinColumn
	private Store store;
	
	private String customerAddress;
	
	@Column(name = "active")
    private int active;
	    public int getActive() {
		return active;
	    }
	    
	//@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Can't be blank")
	private String password;

	@NotEmpty(message = "*Can't be blank")
	private String name;
	
	
	@Transient
    private String newPassword;
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Transient
    private String rePassword;
	
	private Date lastLogin; 
	
	private String resetToken;
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public Customer(int id, @NotEmpty(message = "*Can't be blank") String email, String contactNo, Role role,
			Set<Role> roles, Store store, String customerAddress, int active,
			@NotEmpty(message = "*Can't be blank") String password, @NotEmpty(message = "*Can't be blank") String name,
			String newPassword, String rePassword, Date lastLogin, String resetToken, boolean enabled) {
		super();
		this.id = id;
		this.email = email;
		this.contactNo = contactNo;
		this.role = role;
		this.roles = roles;
		this.store = store;
		this.customerAddress = customerAddress;
		this.active = active;
		this.password = password;
		this.name = name;
		this.newPassword = newPassword;
		this.rePassword = rePassword;
		this.lastLogin = lastLogin;
		this.resetToken = resetToken;
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", contactNo=" + contactNo + ", role=" + role + ", roles="
				+ roles + ", store=" + store + ", customerAddress=" + customerAddress + ", active=" + active
				+ ", password=" + password + ", name=" + name + ", newPassword=" + newPassword + ", rePassword="
				+ rePassword + ", lastLogin=" + lastLogin + ", resetToken=" + resetToken + ", enabled=" + enabled + "]";
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private boolean enabled;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

		public Customer() {
		
	}

		public String getContactNo() {
			return contactNo;
		}

		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

	
	

	
	


	
}
