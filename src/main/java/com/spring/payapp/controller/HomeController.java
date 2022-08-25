/**
 * 
 */
package com.spring.payapp.controller;

import java.time.LocalDate;
import java.util.List;

import com.spring.payapp.domain.entity.*;
import com.spring.payapp.domain.entity.dto.AccountDto;
import com.spring.payapp.domain.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.payapp.domain.entity.Account.Role;
import com.spring.payapp.domain.entity.input.InputForm;
import com.spring.payapp.domain.service.IAccountService;
import com.spring.payapp.domain.service.ITransactionService;
import com.spring.payapp.domain.service.IUserService;
import com.spring.payapp.domain.service.IWalletService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author chun
 * @package payApp
 * @time 4:42:17 PM
 */
@Controller
public class HomeController {

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ITransactionService transactionService;


	@GetMapping("/")
	public String home(ModelMap map, RedirectAttributes redirectAttributes) {
		Usr loginUser = null;
		String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		loginUser = userService.findUserByAccount(loginAccount);
//		UserDto.builder().userID(1).userName("root").phoneNo("09450933516").gender(Gender.Male).account()
		if (loginUser == null ) {
			return "redirect:/user/createUser";
		}
		if ( !isUser(loginAccount) ) {
			return "redirect:/admin";
		}
		Wallet wallet = walletService.findByUserID(loginUser.getUserID());

		List<Transaction> request = transactionService.findByUserIdAndType(loginUser.getUserID(),
				TransactionType.Request);
		List<Transaction> transactions = transactionService.findtransactionsByloginUser(loginUser.getUserID(),
				loginUser.getUserID(), TransactionStatus.Success);
		map.addAttribute("wallet", wallet);
		map.addAttribute("transactions", transactions);
		map.addAttribute("transactionsRequest", request);
		map.addAttribute("inputForm", new InputForm());
		map.addAttribute("loginAccount", loginAccount);
		map.addAttribute("loginUser", loginUser);
		return "home";
		
	}

	public boolean isAdmin(Account account) {
		if (account.getRole().equals(Role.ROLE_ADMIN)){
			return true;
		}
		return false;
	}
	public boolean isUser(Account account) {
		if (account.getRole().equals(Role.ROLE_USER)){
			return true;
		}
		return false;
	}

}
