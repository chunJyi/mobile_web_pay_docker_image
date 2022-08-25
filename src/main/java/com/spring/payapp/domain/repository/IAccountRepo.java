/**
 * 
 */
package com.spring.payapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Account.Role;

/**
 * @author chun
 * @package payApp
 * @time 3:20:40 PM
 */

public interface IAccountRepo extends JpaRepository<Account, Integer> {

	/**
	 * @param email
	 */
	Account findByEmail(String email);

	/**
	 * @param roleUser
	 * @return List<Account> 
	 */
	List<Account> findByRole(Role roleUser);
	
	

}
