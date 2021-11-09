package com.user.UserServiceMS.validator;

import java.util.regex.*;

import com.user.UserServiceMS.dto.BuyerDTO;
import com.user.UserServiceMS.dto.SellerDTO;
import com.user.UserServiceMS.exception.UserException;

public class ValidateUser {
	
	public static void validate_buyer(BuyerDTO buyer) throws UserException
	{
		
		if(!validateName(buyer.getName()))
			throw new UserException("Validator.INVALID_BUYER_NAME");
		if(!validateEmail(buyer.getEmail()))
			throw new UserException("Validator.INVALID_EMAIL_ID");
		if(!validatePhoneNumber(buyer.getPhoneNumber()))
			throw new UserException("Validator.INVALID_PHONE_NUMBER");
		if(!validatePassword(buyer.getPassword()))
			throw new UserException("Validator.INVALID_PASSWORD");
		
	}
	
	
	public static void validate_seller(SellerDTO seller) throws UserException
	{
		if(!validateName(seller.getName()))
			throw new UserException("Validator.INVALID_SELLER_NAME");
		if(!validateEmail(seller.getEmail()))
			throw new UserException("Validator.INVALID_SELLER_EMAIL_ID");
		if(!validatePhoneNumber(seller.getPhoneNumber()))
			throw new UserException("Validator.INVALID_SELLER_PHONE_NUMBER");
		if(!validatePassword(seller.getPassword()))
			throw new UserException("Validator.INVALID_SELLER_PASSWORD");
	}
	
	
	
	public static Boolean validateName(String Name){
		if((!Name.equals("")) && (Name != null) && (Name.matches("^[a-zA-Z]*$"))) 
			return true;
		else 
			return false;
	}
	
	public static Boolean validateEmail(String Email) {
		String regex = "^[\\w-\\.+]*[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if(Email.matches(regex))
			return true;
		else
			return false;
	}

	public static Boolean validatePhoneNumber(String phn) {
	
		Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
	    Matcher match = ptrn.matcher(phn);
	    if(match.find() && match.group().equals(phn))
	        return true;
	    else
	        return false;
	}
	
	public static Boolean validatePassword(String Password) {
		String p ="^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[!@#&()–[{}]:;',?/*~$^+=<>]).{7,20}$";
		Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(Password);
        if(matcher.matches())
        	return true;
        else
            return false;
	}
}
