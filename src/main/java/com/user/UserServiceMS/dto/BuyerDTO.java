package com.user.UserServiceMS.dto;

import com.user.UserServiceMS.entity.Buyer;

public class BuyerDTO {
	
	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	private String isPrivileged;
	private String rewardPoints;
	private String isActive;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsPrivileged() {
		return isPrivileged;
	}
	public void setIsPrivileged(String isPrivileged) {
		this.isPrivileged = isPrivileged;
	}
	public String getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(String rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public static BuyerDTO valueOf(Buyer buyer) {
		BuyerDTO buyerDTO = new BuyerDTO();
//		buyerDTO.setBuyerId(buyer.getBuyerId());
		buyerDTO.setName(buyer.getName());
		buyerDTO.setEmail(buyer.getEmail());
		buyerDTO.setPhoneNumber(buyer.getPhoneNumber());
		buyerDTO.setPassword(buyer.getPassword());
		buyerDTO.setIsPrivileged(buyer.getIsPrivileged());
		buyerDTO.setRewardPoints(buyer.getRewardPoints());
		buyerDTO.setIsActive(buyer.getIsActive());
		return buyerDTO;
	}
	

}