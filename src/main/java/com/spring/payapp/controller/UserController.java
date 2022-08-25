/**
 * 
 */
package com.spring.payapp.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.payapp.common.Common;
import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Usr;
import com.spring.payapp.domain.entity.Wallet;
import com.spring.payapp.domain.entity.dto.UserDto;
import com.spring.payapp.domain.entity.input.UserForm;
import com.spring.payapp.domain.repository.IWalletRepo;
import com.spring.payapp.domain.service.IAccountService;
import com.spring.payapp.domain.service.IUserService;

/**
 * @author chun
 * @package payApp
 * @time 3:47:50 PM
 */
@Controller
public class UserController {
	
	@Autowired
	private Common common;
	
	@Autowired
	private IUserService userService;
	
	
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IWalletRepo walletRepo;
	
	@GetMapping("/user/createUser")
	public String createNewUser(ModelMap map) {
		
		map.addAttribute("createUser",new UserForm());
		
		return "app-user-form";
	}
	
	
	@PostMapping("/user/createUser")
	@Transactional
	public String createNewUser(@ModelAttribute @Valid UserForm userForm,BindingResult result) {
		if (result.hasErrors()) {
			return "app-user-form";
		}
		String loginAccountName= SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount=accountService.findAccountByEmail(loginAccountName);
		UserDto userDto = UserDto.builder()
				.userName(userForm.getUsername())
				.phoneNo(userForm.getPhNo())
				.account(loginAccount)
				.gender(userForm.getGender())
				.build();
		 Usr savedUser=userService.createUser(userDto);
		 if (loginAccount.getRole().equals(Account.Role.ROLE_USER)){
			 Wallet wallet= new Wallet();
			 wallet.setWalledUser(savedUser);
			 wallet.setWalletUserID(common.RandomAccountID());
			 wallet.setAmount(15000);
			 walletRepo.save(wallet);
		 }
		return "redirect:/";
	}
	

}
