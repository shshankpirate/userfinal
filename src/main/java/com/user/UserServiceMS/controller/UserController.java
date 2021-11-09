package com.user.UserServiceMS.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.user.UserServiceMS.dto.BuyerDTO;
import com.user.UserServiceMS.dto.CartDTO;
import com.user.UserServiceMS.dto.ProductDTO;
import com.user.UserServiceMS.dto.SellerDTO;
import com.user.UserServiceMS.exception.UserException;
import com.user.UserServiceMS.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userServiceNew;
	
	@Autowired
	Environment environment;
	
	@Value("${product.uri}")
	String prodUri;
	//all buyers
			@GetMapping(value="/buyers", produces = MediaType.APPLICATION_JSON_VALUE)
			public List<BuyerDTO> getAllBuyers(){
				return userServiceNew.getAllBuyers();
			}
			//all sellers
			@GetMapping(value="/sellers", produces = MediaType.APPLICATION_JSON_VALUE)
			public List<SellerDTO> getAllSellers(){
				return userServiceNew.getAllSellers();
			}
	//to register buyer 
	@PostMapping(value = "/buyer/registeration")
	public ResponseEntity<String> registerBuyer(@RequestBody BuyerDTO buyerDto){
		
		try {
		String s ="Buyer registered successfully with buyer Id : " + userServiceNew.buyerRegistration(buyerDto);
		return new ResponseEntity<>(s,HttpStatus.OK);
		}
		catch(UserException e)
		{
			String s = environment.getProperty(e.getMessage());
			return new ResponseEntity<>(s,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	//to register seller
	@PostMapping(value = "/userMS/seller/register")
	public ResponseEntity<String> registerSeller(@RequestBody SellerDTO sellerDto){
		
		try {
		String s ="Seller registered successfully with seller Id : "+ userServiceNew.sellerRegistration(sellerDto);
		return new ResponseEntity<>(s,HttpStatus.OK);
		}
		catch(UserException e)
		{
			return new ResponseEntity<>(environment.getProperty(e.getMessage()),HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	//to login buyer using email and password
	@PostMapping(value = "/userMS/buyer/login/{email}/{pwd}")
	public ResponseEntity<String> loginBuyer(@PathVariable String email, @PathVariable String password)
	{
		try {
			String msg = userServiceNew.buyerLogin(email, password);
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		catch(UserException e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	//to login seller using email and password
	@PostMapping(value = "/userMS/seller/login/{email}/{pwd}")
	public ResponseEntity<String> loginSeller(@PathVariable String email, @PathVariable String password)
	{
		try {
			String msg = userServiceNew.sellerLogin(email, password);
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}
		catch(UserException e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	//deactivate buyer(buyer id)
	@DeleteMapping(value = "/userMS/buyer/deactivate/{id}")
	public ResponseEntity<String> deleteBuyerAccount(@PathVariable String id){
		
		String msg = userServiceNew.deleteBuyer(id);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	//deactivate seller(seller id)
	@DeleteMapping(value = "/userMS/seller/deactivate/{id}")
	public ResponseEntity<String> deleteSellerAccount(@PathVariable String id){
		
		String msg = userServiceNew.deleteSeller(id);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	//add product(product id) to wishlist of buyer(buyer id)
	@PostMapping(value = "/userMS/buyer/wishlist/add/{buyerId}/{prodId}")
	public ResponseEntity<String> addProductToWishlist(@PathVariable String buyerId, @PathVariable String prodId) throws UserException
	{
		try {
		
		ProductDTO product = new RestTemplate().getForObject(prodUri+"/prodMS/getById/"+prodId, ProductDTO.class);
		
		String msg = userServiceNew.wishlistService(product.getProdId(), buyerId);
		
		return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			System.out.println(e);
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product ID";
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,newMsg,e);
		}
	}
	
	//add product(product id) & quantity(quantity) to buyer(buyer id) cart 
	@PostMapping(value = "/userMS/buyer/cart/add/{buyerId}/{prodId}/{quantity}")
	public ResponseEntity<String> addProductToCart(@PathVariable String buyerId, @PathVariable String prodId, @PathVariable Integer quantity) throws UserException
	{
		try {
		
		ProductDTO product = new RestTemplate().getForObject(prodUri+"/prodMS/getById/"+prodId, ProductDTO.class);
		System.out.println(product);
		System.out.println(product instanceof ProductDTO);
		String msg = userServiceNew.cartService(product.getProdId(), buyerId, quantity);
		
		return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product ID";
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,newMsg,e);
		}
	}
	
	//get cart details of buyer(buyer id)
	@GetMapping(value = "/userMS/buyer/cart/get/{buyerId}")
	public ResponseEntity<List<CartDTO>> getProductListFromCart(@PathVariable String buyerId) throws UserException
	{
		
		try {
		List<CartDTO> list = userServiceNew.getCartProducts(buyerId);
		
		return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
		}
		catch(UserException e)
		{
			System.out.println(e.getMessage());
			String msg = e.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
			
		}
	}
	
	//to remove product(product id) from buyer's(buyer id) cart
	@PostMapping(value = "/userMS/buyer/cart/remove/{buyerId}/{prodId}")
	public ResponseEntity<String> removeFromCart(@PathVariable String buyerId,@PathVariable String prodId) throws UserException
	{
		
		try {
		String msg = userServiceNew.removeFromCart(buyerId, prodId);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		catch(UserException e)
		{
			String msg = e.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
			
		}
	}
		//update reward points of buyer(buyer id)
	@GetMapping(value = "/userMS/updateRewardPoints/{buyerId}/{rewardPoints}")
	public ResponseEntity<String> updateRewardPoints(@PathVariable String buyerId, @PathVariable Integer rewPoints)
	{
		try {
			String msg = userServiceNew.updateRewardPoint(buyerId, rewPoints);
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
		}
	}
	
	
}
