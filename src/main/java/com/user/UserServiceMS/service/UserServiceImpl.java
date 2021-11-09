package com.user.UserServiceMS.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.UserServiceMS.dto.BuyerDTO;
import com.user.UserServiceMS.dto.CartDTO;
import com.user.UserServiceMS.dto.SellerDTO;
import com.user.UserServiceMS.entity.Buyer;
import com.user.UserServiceMS.entity.Cart;
import com.user.UserServiceMS.entity.Seller;
import com.user.UserServiceMS.entity.Wishlist;
import com.user.UserServiceMS.exception.UserException;
import com.user.UserServiceMS.repository.BuyerRepo;
import com.user.UserServiceMS.repository.CartRepo;
import com.user.UserServiceMS.repository.SellerRepo;
import com.user.UserServiceMS.repository.WishlistRepo;
import com.user.UserServiceMS.utility.PrimaryKey;
import com.user.UserServiceMS.validator.ValidateUser;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	
	private static int bIndex;
	private static int sIndex;
	
	static {
		bIndex=100;
		sIndex=100;
	}
	
	@Autowired
	private BuyerRepo buyerRepository;
	
	@Autowired
	private SellerRepo sellerRepository;

	@Autowired
	private WishlistRepo wishlistRepository;
	
	@Autowired
	private CartRepo cartRepository;
	//all buyers
		@Override
		public List<BuyerDTO> getAllBuyers(){
			List<Buyer> b=(List<Buyer>) buyerRepository.findAll();
			List<BuyerDTO> bDTOs= new ArrayList<>();
			for(Buyer bb:b) {
				BuyerDTO bDTO=BuyerDTO.valueOf(bb);
				bDTOs.add(bDTO);
			}
			//logger.info("Buyer Details:{}",bDTOs);
			return bDTOs;
		}
		//all sellers
		@Override
		public List<SellerDTO> getAllSellers(){
			List<Seller> s=(List<Seller>) sellerRepository.findAll();
			List<SellerDTO> sDTOs=new ArrayList<>();
			for(Seller ss:s) {
				SellerDTO sDTO=SellerDTO.valueOf(ss);
				sDTOs.add(sDTO);
				
			}
			//logger.info("Seller Details are:{}", sDTOs);
			return sDTOs;
		}
	@Override
	public String buyerRegistration(BuyerDTO buyerDTO) throws UserException {
		// TODO Auto-generated method stub
		
		ValidateUser.validate_buyer(buyerDTO);
		
		Buyer buy = buyerRepository.findByPhoneNumber(buyerDTO.getPhoneNumber());
		
		if(buy != null)
			throw new UserException("Buyer already present");
		
		String id = "B" + bIndex++;
		
		buy = new Buyer();
		
		buy.setBuyerId(id);
		buy.setEmail(buyerDTO.getEmail());
		buy.setName(buyerDTO.getName());
		buy.setPhoneNumber(buyerDTO.getPhoneNumber());
		buy.setPassword(buyerDTO.getPassword());
		buy.setIsActive("False");
		buy.setIsPrivileged("False");
		buy.setRewardPoints("0");
		
		buyerRepository.save(buy);
		
		return buy.getBuyerId();
	}

	@Override
	public String sellerRegistration(SellerDTO sellerDTO) throws UserException {
		// TODO Auto-generated method stub
		
		ValidateUser.validate_seller(sellerDTO);
		
		Seller seller = sellerRepository.findByPhoneNumber(sellerDTO.getPhoneNumber());
		
		if(seller != null)
			throw new UserException("Seller Already present");
		
		String id = "S" + sIndex++;
		
		seller = new Seller();
		
		seller.setEmail(sellerDTO.getEmail());
		seller.setSellerId(id);
		seller.setName(sellerDTO.getName());
		seller.setPassword(sellerDTO.getPassword());
		seller.setIsActive("False");
		seller.setPhoneNumber(sellerDTO.getPhoneNumber());
		
		sellerRepository.save(seller);
		
		return seller.getSellerId();
	}

	@Override
	public String buyerLogin(String email, String password) throws UserException {
		// TODO Auto-generated method stub
	
		if(!ValidateUser.validateEmail(email))
			throw new UserException("Enter valid email id");
		
		Buyer buyer = buyerRepository.findByEmail(email);
		
//		System.out.println(buyer);
		
		if(buyer == null)
			throw new UserException("Wrong credentials");
		
		if(!buyer.getPassword().equals(password))
			throw new UserException("Wrong credentials");
		
		buyer.setIsActive("True");
			
		buyerRepository.save(buyer);
		
		return "Login Success";
	}

	@Override
	public String sellerLogin(String email, String password) throws UserException {

//		if(!ValidateUser.validateEmail(email))
//			throw new UserException("Enter valid email id");
		
		Seller seller = sellerRepository.findByEmail(email);
		
//		System.out.println(seller);
		
		if(seller == null)
			throw new UserException("SELLER.WRONGCREDENTIALS");
		
		if(!seller.getPassword().equals(password))
			throw new UserException("SELLER.WRONGCREDENTIALS");
		
		seller.setIsActive("True");
			
		sellerRepository.save(seller);
		
		return "Login Success";
	}

	@Override
	public String deleteBuyer(String id){
		
		Buyer buyer = buyerRepository.findByBuyerId(id);
		
		buyerRepository.delete(buyer);
		
		return "Account successfully deleted";
	}

	@Override
	public String deleteSeller(String id) {
		
		Seller seller = sellerRepository.findBySellerId(id);
		
		sellerRepository.delete(seller);
		
		return "Account successfully deleted";
	}

	@Override
	public String wishlistService(String prodId, String buyerId) {
		
		PrimaryKey cust = new PrimaryKey(prodId,buyerId);
	
		Wishlist w = new Wishlist();
		
		w.setCustomId(cust);
		
		wishlistRepository.save(w);
		
		return "Added Successfully to Wishlist";
	}
	
	@Override
	public String cartService(String prodId, String buyerId, Integer quantity) {
		
		PrimaryKey cust = new PrimaryKey(prodId,buyerId);
	
		Cart cart = new Cart();
		
		cart.setCustomPK(cust);
		
		cart.setQuantity(quantity);
		
		cartRepository.save(cart);
		
		return "Added Successfully to Cart";
	}

	@Override
	public List<CartDTO> getCartProducts(String id) throws UserException {
		
		List<Cart> list = cartRepository.findByCustomPKBuyerId(id);
		
		if(list.isEmpty())
			throw new UserException("No Items In Cart");
		
		List<CartDTO> li = new ArrayList<>();
		
		for(Cart cart : list)
		{
			CartDTO cartDTO = new CartDTO();
			
			cartDTO.setBuyerId(cart.getCustomPK().getBuyerId());
			cartDTO.setProdId(cart.getCustomPK().getProdId());
			cartDTO.setQuantity(cart.getQuantity());
			
			li.add(cartDTO);
		}
		
		return li;
	}

	@Override
	public String removeFromCart(String buyerId, String prodId) throws UserException {
		// TODO Auto-generated method stub
		
		Cart cart = cartRepository.findByCustomPKBuyerIdAndCustomPKProdId(buyerId, prodId);
		
		if(cart==null)
			throw new UserException("No Such Item In Cart");
		
		cartRepository.deleteByCustomPKBuyerIdAndCustomPKProdId(buyerId, prodId);
		
		return "The product was deleted successfully";
	}

	@Override
	public String updateRewardPoint(String buyerId, Integer rewPoints) throws UserException {
		// TODO Auto-generated method stub
		
		Buyer buyer = buyerRepository.findByBuyerId(buyerId);
		
		if(buyer==null)
			throw new UserException("Buyer not found");
		
		buyer.setRewardPoints(rewPoints.toString());
		
		buyerRepository.save(buyer);
		
		return "Reward Points Updated for buyer Id : "+ buyer.getBuyerId();
	}

}