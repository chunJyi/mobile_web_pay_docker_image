/**
 * 
 */
package com.spring.payapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Usr;
import com.spring.payapp.domain.entity.Wallet;
import com.spring.payapp.domain.service.IAccountService;
import com.spring.payapp.domain.service.IUserService;
import com.spring.payapp.domain.service.IWalletService;

/**
 * @author chun
 * @package payApp
 * @time 2:50:32 PM
 */
@Controller
public class WalletController {
	
	@Autowired
	private IAccountService accountService;
	
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IWalletService walletService;
	
	@GetMapping("/user/card")
	public String walletPage(ModelMap map) {
		String loginAccountName = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account=accountService.findAccountByEmail(loginAccountName);
		Usr loginUser=userService.findUserByAccount(account);
		
		Wallet wallet=walletService.findByUserID(loginUser.getUserID());
		map.addAttribute("wallet",wallet);
		return "app-cards";
	}

}
