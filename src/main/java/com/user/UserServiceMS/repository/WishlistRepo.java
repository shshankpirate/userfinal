package com.user.UserServiceMS.repository;

import org.springframework.data.repository.CrudRepository;

import com.user.UserServiceMS.entity.Wishlist;
import com.user.UserServiceMS.utility.PrimaryKey;

public interface WishlistRepo extends CrudRepository<Wishlist, PrimaryKey> {

}
