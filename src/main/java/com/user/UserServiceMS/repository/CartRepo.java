package com.user.UserServiceMS.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.user.UserServiceMS.entity.Cart;
import com.user.UserServiceMS.utility.PrimaryKey;

public interface CartRepo extends CrudRepository<Cart, PrimaryKey> {
	
	
	public List<Cart> findByCustomPKBuyerId(String id); 
	
	public void deleteByCustomPKBuyerIdAndCustomPKProdId(String buyId,String prodId);
	
	public Cart findByCustomPKBuyerIdAndCustomPKProdId(String buyId,String ProdId);

}
