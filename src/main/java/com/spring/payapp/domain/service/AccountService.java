package com.spring.payapp.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Account.Role;
import com.spring.payapp.domain.entity.dto.AccountDto;
import com.spring.payapp.domain.repository.IAccountRepo;
import com.spring.payapp.exception.UserNotFoundException;

/**
 * @author chun
 * @package payApp
 * @time 3:54:42 PM
 */
@Service
public class AccountService implements IAccountService  {

	@Autowired
	private PasswordEncoder encode;

	@Autowired
	private IAccountRepo accountRepo;

	
	@Override
	public Account saveUser(AccountDto dto) {
		 
		 Account account = Account.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.createdDate(dto.getCreatedDate())
				.updatedDate(LocalDate.now())
				.role(dto.getRole())
				.enable(dto.isEnable())
				.build();
		accountRepo.saveAndFlush(account);	
		return account;
	}
	
	@Override
	public Account updateUser(AccountDto dto) {
		 
		 Account account = Account.builder()
				 .accountID(dto.getAccountID())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.createdDate(dto.getCreatedDate())
				.updatedDate(LocalDate.now())
				.role(dto.getRole())
				.enable(dto.isEnable())
				.build();
		accountRepo.saveAndFlush(account);	
		return account;
	}
	
	
	@Override
	public void memberManagement(Account account) {
		accountRepo.save(account);
	}
	
	@Override
	public void updatePassword(AccountDto accountDto) {
		Account account=Account.builder().accountID(accountDto.getAccountID()).email(accountDto.getEmail()).password(encode.encode(accountDto.getPassword())).createdDate(accountDto.getCreatedDate()).updatedDate(accountDto.getUpdatedDate()).enable(true).role(accountDto.getRole()).build();
		accountRepo.save(account);
	}
	
	@Override
	public Account findAccountByEmail(String email) {
		return accountRepo.findByEmail(email);
	}
	
	public List<Account> findByRoleOfUser(Role roleUser) {
		return accountRepo.findByRole(roleUser);
		
	}
	
	public List<Account> findAll() {
		return accountRepo.findAll();
	}
	
	public Account findById(int id) throws UserNotFoundException{
		return accountRepo.findById(id).orElseThrow(() -> new UserNotFoundException("UserNotFound By " + id));
	}

}
