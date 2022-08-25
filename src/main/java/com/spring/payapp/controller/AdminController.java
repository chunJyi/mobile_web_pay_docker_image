/**
 * 
 */
package com.spring.payapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.spring.payapp.domain.entity.Usr;
import com.spring.payapp.domain.service.IUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Account.Role;
import com.spring.payapp.domain.entity.Wallet;
import com.spring.payapp.domain.entity.dto.AccountDto;
import com.spring.payapp.domain.entity.input.ChangePasswordForm;
import com.spring.payapp.domain.entity.input.SignUpForm;
import com.spring.payapp.domain.service.IAccountService;
import com.spring.payapp.domain.service.IWalletService;
import com.spring.payapp.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

/**
 * @author chun
 * @package payApp
 * @time 3:36:11 PM
 */
@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private static int accountID;

	private IAccountService accountService;
	private IWalletService walletService;
	private PasswordEncoder encode;
	private IUserService userService;


	@GetMapping("")
	public String adminHomePage(ModelMap map) {

		String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		Usr loginUser = userService.findUserByAccount(loginAccount);

		List<Account> accounts = accountService.findAll();
		List<Account> members = accounts.stream().filter(a -> a.getRole().equals(Role.ROLE_MEMBER))
				.collect(Collectors.toList());
		List<Account> users = accounts.stream().filter(a -> a.getRole().equals(Role.ROLE_USER))
				.collect(Collectors.toList());

		List<Wallet> wallets = walletService.findAll();
		map.addAttribute("loginUser", loginUser);
		map.addAttribute("members", members);

		map.addAttribute("membersCount", members.size());
		map.addAttribute("wallets", wallets);
		map.addAttribute("usersCount", wallets.size());
		return "admin/app-adminDashboard";
	}

	@GetMapping("/createMember")
	public String createMemeber(ModelMap map) {
		map.addAttribute("signUpForm", new SignUpForm());
		return "admin/app-createMemberForm";
	}

	@PostMapping("/createMember")
	public String createMemeber(@ModelAttribute @Valid SignUpForm form, ModelMap map, BindingResult result,
			RedirectAttributes attribute) {
		if (result.hasErrors()) {
			return "redirect:/createMember";
		}
		AccountDto dto = AccountDto.builder().email(form.getEmail())
				.password(encode.encode(form.getPassword())).createdDate(LocalDate.now())
				.updatedDate(LocalDate.now()).enable(true).role(Role.ROLE_MEMBER).build();
		accountService.saveUser(dto);
		
		attribute.addFlashAttribute("message", "Created Member!");
		return "redirect:/admin";

	}

	@GetMapping("memberManagement/{id}")
	public String memberManagement(@PathVariable(value = "id") int id, ModelMap map, RedirectAttributes attribute) {
		Account account;
		try {
			account = accountService.findById(id);
		} catch (UserNotFoundException e) {
			map.addAttribute("message", e.getMessage());
			return "redirect:/admin";
		}
		if (account.isEnable()) {
			account.setEnable(false);
		} else {
			account.setEnable(true);

		}
		accountService.memberManagement(account);
		attribute.addFlashAttribute("memberManagementMessage", "Success!");
		return "redirect:/admin";
	}

	@GetMapping("memberChangePassword/{id}")
	public String memberChangePassword(@PathVariable(value = "id") int id, ModelMap map) {
		accountID = id;
		map.addAttribute("form", new ChangePasswordForm());
		return "admin/app-memberPasswordChange";
	}

	@GetMapping("userManagement/{id}")
	public String userManagement(@PathVariable(value = "id") int id, ModelMap map, RedirectAttributes attribute) {
		Account account;
		try {
			account = accountService.findById(id);
		} catch (UserNotFoundException e) {
			map.addAttribute("message", e.getMessage());
			return "redirect:/admin";
		}
		if (account.isEnable()) {
			account.setEnable(false);
		} else {
			account.setEnable(true);

		}
		accountService.memberManagement(account);
		attribute.addFlashAttribute("userManagementMessage", "Success!");
		return "redirect:/admin";
	}

	@PostMapping("/memberChangePassword")
	public String memberChangePassword(@ModelAttribute ChangePasswordForm form, ModelMap map,
			RedirectAttributes attribute) {
		String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);

		if (encode.matches(form.getConfirmPassword(), loginAccount.getPassword())) {
			try {
				Account member = accountService.findById(accountID);
				AccountDto dto = AccountDto.builder().accountID(member.getAccountID()).email(member.getEmail())
						.password(encode.encode(form.getNewPassword())).createdDate(member.getCreatedDate())
						.updatedDate(LocalDate.now()).enable(true).role(member.getRole()).build();
				accountService.saveUser(dto);
			} catch (UserNotFoundException e) {
				map.addAttribute("form", new ChangePasswordForm());
				map.addAttribute("changePasswordMessage", "Wrong Your Password");
				return "admin/app-memberPasswordChange";
			}

		}
		return loginEmail;
	}

}
