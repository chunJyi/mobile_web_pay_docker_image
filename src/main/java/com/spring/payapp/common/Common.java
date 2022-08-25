/**
 * 
 */
package com.spring.payapp.common;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.spring.payapp.domain.entity.input.InputForm;

/**
 * @author chun
 * @package payApp
 * @time 8:08:09 PM
 */
@ControllerAdvice
public class Common {

	
	
	
	@Bean
	public String RandomAccountID() {
		Random random = new Random();
    	int id = random.nextInt(99999999);
    	String date=LocalDate.now().toString().replace("-", "");
    	String StringID = String.format("%06d", id);
    	String walletId=date.concat(StringID);
    	System.out.println(walletId);
    
    
    	return walletId;
   
	}
	
//	private ITransactionService transactionService;
//	
	
	@ModelAttribute(name = "inputForm")
	public InputForm inputForm() {
	return	new InputForm();
	}
	
	

}
