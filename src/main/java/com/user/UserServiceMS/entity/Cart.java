package com.user.UserServiceMS.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.user.UserServiceMS.utility.PrimaryKey;

@Entity
@Table(name = "cart")
public class Cart {
	
	@EmbeddedId
	private PrimaryKey customPK;
	
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public PrimaryKey getCustomPK() {
		return customPK;
	}

	public void setCustomPK(PrimaryKey customPK) {
		this.customPK = customPK;
	}

		
	
}

