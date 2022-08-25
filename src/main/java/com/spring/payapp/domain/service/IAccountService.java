/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.dto.AccountDto;
import com.spring.payapp.exception.UserNotFoundException;

/**
 * @author chun
 * @package payApp
 * @time 3:54:17 PM
 */
@Service
public interface IAccountService {

	/**
	 * @param email
	 * @return
	 */
	Account findAccountByEmail(String email);


	/**
	 * @param accountDto
	 */
	void updatePassword(AccountDto accountDto);


	/**
	 * @param account
	 */
	void memberManagement(Account account);

	/**
	 * @return
	 */
	List<Account> findAll();

	/**
	 * @param id
	 * @return
	 * @throws UserNotFoundException 
	 */
	Account findById(int id) throws UserNotFoundException;

	/**
	 * @param dto
	 * @return 
	 */
	Account saveUser(AccountDto dto);


	/**
	 * @param dto
	 * @return
	 */
	Account updateUser(AccountDto dto);
	
	

}
