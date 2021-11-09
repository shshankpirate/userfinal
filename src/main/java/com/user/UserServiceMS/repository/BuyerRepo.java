package com.user.UserServiceMS.repository;

import org.springframework.data.repository.CrudRepository;

import com.user.UserServiceMS.entity.Buyer;

public interface BuyerRepo extends CrudRepository<Buyer, String> {
	
	public Buyer findByPhoneNumber(String phoneNumber);
	
	public Buyer findByEmail(String email);
	
	public Buyer findByBuyerId(String id);

}
