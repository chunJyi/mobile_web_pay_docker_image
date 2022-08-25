/**
 * 
 */

package com.spring.payapp.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.spring.payapp.domain.entity.*;
import com.spring.payapp.domain.repository.IAccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.payapp.domain.entity.Account.Role;
import com.spring.payapp.domain.entity.dto.AccountDto;
import com.spring.payapp.domain.entity.dto.TokenDto;
import com.spring.payapp.domain.entity.dto.UserDto;
import com.spring.payapp.domain.entity.input.SignInForm;
import com.spring.payapp.domain.entity.input.SignUpForm;
import com.spring.payapp.domain.service.IAccountService;
import com.spring.payapp.domain.service.IResetPasswordKeyService;
import com.spring.payapp.domain.service.ITokenService;
import com.spring.payapp.domain.service.IUserService;
import com.spring.payapp.exception.UserNotFoundException;
import com.spring.payapp.mailUtil.MailHelper;

/**
 * @author chun
 * @package payApp
 * @time 3:31:03 PM
 */
@Controller
@AllArgsConstructor
public class AccountController {

	private String email;


	private final IAccountRepo accountRepo;

	private final IAccountService accountService;
	private final PasswordEncoder encode;
	private final ITokenService tokenService;
	private final IResetPasswordKeyService keyService;
	private final IUserService userService;
	private final MailHelper mailHelper;

	public String getLoginAccountName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@GetMapping("/signUp")
	public String signUp(ModelMap map) {
        if (accountService.findAccountByEmail("root@gmail.com")==null) {
            Account account = accountService.saveUser(AccountDto.builder().accountID(1).email("root@gmail.com").password(encode.encode("root")).createdDate(LocalDate.now()).updatedDate(LocalDate.now()).enable(true).role(Role.ROLE_ADMIN).build());
            userService.createUser(UserDto.builder().userID(1).account(account).userName("root").gender(Gender.Male).phoneNo("09450933516").build());
        }
		map.addAttribute("signUpForm", new SignUpForm());
		return "auth/app-register";

	}

	@PostMapping("/signUp")
	@Transactional
	public String signUp(@ModelAttribute(name = "signUpForm") @Valid SignUpForm signUpForm,
			RedirectAttributes redirect, BindingResult result) {
		if (result.hasErrors()) {
			return "auth/app-register";
		}

		if (accountService.findAccountByEmail(signUpForm.getEmail()) != null) {
			redirect.addFlashAttribute("message", "Your email is taken!");
			return "auth/app-register";
		}
		AccountDto Dto = AccountDto.builder().email(signUpForm.getEmail())
				.password(encode.encode(signUpForm.getPassword())).createdDate(LocalDate.now())
				.updatedDate(LocalDate.now()).enable(false).role(Role.ROLE_USER).build();
		Account account = accountService.saveUser(Dto);
		TokenDto tokenDto = TokenDto.builder().account(account).token(UUID.randomUUID().toString())
				.expireTime(LocalTime.now().plusMinutes(1)).build();
		tokenService.saveToken(tokenDto);
		String url ="https://localhost:8080/verificationMail/" + tokenDto.getToken();
		mailHelper.sendMail(account.getEmail(), "Account Confirmation", url);
		redirect.addFlashAttribute("message", "Check Your mail for Account verification..!");
		return "redirect:/login";

	}

	@GetMapping(path = "/verificationMail/{token}")
	public String verification(@PathVariable String token, RedirectAttributes redirectAttributes) {
		Token t = tokenService.findByToken(token);
		Account account;
		try {
			account = accountService.findById(t.getAccount().getAccountID());
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "Your Toke is not Correct!");
			return "redirect:/login";
		}
		if (t.isExpireTime()) {
			redirectAttributes.addFlashAttribute("message", "Your Toke is Expire Time!");
			return "redirect:/login";
		}
		AccountDto accountDto = AccountDto.builder().accountID(account.getAccountID()).email(account.getEmail())
				.password(account.getPassword()).createdDate(account.getCreatedDate())
				.updatedDate(account.getUpdatedDate()).enable(true).role(account.getRole()).build();
		accountService.updateUser(accountDto);
		redirectAttributes.addFlashAttribute("message", "Verified Your Account.!");
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String signIn(ModelMap map) {
		map.addAttribute("signInForm", new SignInForm());
		return "auth/app-login";
	}

	@PostMapping("/user/forgotPassword")
	public String forgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
		Account loginAccount = accountService.findAccountByEmail(email);
		if (loginAccount != null) {
			ResetPasswordKey keyByID = keyService.findByAccountAccountID(loginAccount.getAccountID());

			this.email = email;
			Random random = new Random();
			int key = random.nextInt(9999);
			String keyValue = String.valueOf(key);
			System.out.println(keyValue);
			TokenDto tokenDto;
			if (keyByID != null) {
				tokenDto = TokenDto.builder().id(keyByID.getId()).account(loginAccount).token(keyValue)
						.expireTime(LocalTime.now().plusMinutes(1)).build();
			}else tokenDto = TokenDto.builder().account(loginAccount).token(keyValue)
					.expireTime(LocalTime.now().plusMinutes(1)).build();
			
			String url = tokenDto.getToken();
			mailHelper.sendMail(email,"Account Confirmation", url);
			
			keyService.updateKey(tokenDto);

			return "app-email-verification";
		}
		redirectAttributes.addFlashAttribute("message", "Your email is not correct.!");
		return "redirect:/login";

	}

	public Boolean check(List<ResetPasswordKey> keys) {
		return keys.stream().map(a -> a.getAccount().getEmail()).collect(Collectors.toList()).contains(email);
	}

	@PostMapping("/user/confirmKey")
	public String confirmKey(@RequestParam int key) {
		List<ResetPasswordKey> keys = keyService.findByToken(String.valueOf(key));
		if (check(keys)) {
			return "app-newPasswordForm";
		}
		return "redirect:/login";
	}

	@GetMapping("/user/profileSettings")
	public String accountSetting(ModelMap map) {
		String loginEmail = getLoginAccountName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		Usr loginUser = userService.findUserByAccount(loginAccount);
		map.addAttribute("loginUser", loginUser);
		map.addAttribute("loginAccount", loginAccount);
		return "app-settings";
	}

	@PostMapping("/user/profileSettingsUpdateName")
	public String accountSettingUpdateName(ModelMap map, @RequestParam String name, RedirectAttributes attributes) {
		String loginEmail = getLoginAccountName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		Usr loginUser = userService.findUserByAccount(loginAccount);
		UserDto dto = UserDto.builder().userID(loginUser.getUserID()).account(loginAccount)
				.gender(loginUser.getGender()).phoneNo(loginUser.getPhoneNo()).userName(name).build();
		userService.createUser(dto);
		map.addAttribute("loginUser", loginUser);
		map.addAttribute("loginAccount", loginAccount);
		attributes.addFlashAttribute("message", "Success Changed Your Name.");

		return "redirect:/user/profileSettings";
	}

	@PostMapping("/user/profileSettingsUpdatePhoneNo")
	public String accountSettingUpdatePhoneNo( @RequestParam String phoneNo,
			RedirectAttributes attributes) {
		if (checkValidPhoneNo(phoneNo)) {
			String loginEmail = getLoginAccountName();
			Account loginAccount = accountService.findAccountByEmail(loginEmail);
			Usr loginUser = userService.findUserByAccount(loginAccount);
			UserDto dto = UserDto.builder().userID(loginUser.getUserID()).account(loginAccount)
					.gender(loginUser.getGender()).phoneNo(phoneNo).userName(loginUser.getUserName()).build();
			userService.createUser(dto);
			attributes.addFlashAttribute("message", "Success Changed Your PhoneNumber.");
			return "redirect:/user/profileSettings";
		}
		attributes.addFlashAttribute("message", "Fail, Your phoneNumber is't valid.");

		return "redirect:/user/profileSettings";

	}

	@PostMapping("/user/createdNewPassword")
	public String createdNewPassword(@RequestParam String password, RedirectAttributes attributes) {
		Account account = accountService.findAccountByEmail(email);
		AccountDto accountDto = AccountDto.builder().accountID(account.getAccountID()).email(account.getEmail())
				.password(password).createdDate(account.getCreatedDate()).updatedDate(LocalDate.now()).enable(true)
				.role(account.getRole()).build();
		accountService.updatePassword(accountDto);
		attributes.addFlashAttribute("message", "Success Changed Your Password.");
		return "redirect:/login";

	}

	private boolean checkValidPhoneNo(String phoneNo) {
		return phoneNo.startsWith("09") && (phoneNo.length() == 11);
	}

	@PostMapping("/user/profileSettingsUpdatePassword")
	public String accountSettingUpdatePassword(@RequestParam String oldPassword,
			@RequestParam String newPassword, RedirectAttributes attributes) {
		String loginEmail = getLoginAccountName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		if (encode.matches(oldPassword, loginAccount.getPassword())) {
			if ((newPassword.length() >= 8)) {
				AccountDto accountDto = AccountDto.builder().accountID(loginAccount.getAccountID())
						.email(loginAccount.getEmail()).password(newPassword).createdDate(loginAccount.getCreatedDate())
						.updatedDate(LocalDate.now()).enable(true).role(loginAccount.getRole()).build();
				accountService.updatePassword(accountDto);
				attributes.addFlashAttribute("message", "Success, Changed Your Password.");
				return "redirect:/user/profileSettings";
			}
			attributes.addFlashAttribute("message", "Fail, Your Password must be greater than 8.!");
			return "redirect:/user/profileSettings";
		}
		attributes.addFlashAttribute("message",
				"Fail, Your Password is not match. or Password must be greater than 8.!");
		return "redirect:/user/profileSettings";
	}

}
