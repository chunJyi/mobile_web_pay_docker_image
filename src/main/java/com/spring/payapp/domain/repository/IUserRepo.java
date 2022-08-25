/**
 * 
 */
package com.spring.payapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payapp.domain.entity.Usr;

/**
 * @author chun
 * @package payApp
 * @time 3:26:22 PM
 */
public interface IUserRepo extends JpaRepository<Usr, Integer> {
	
	
	

	Usr findByAccountAccountID(int accountId);

	/**
	 * @param phNo
	 * @return
	 */
	Usr findByPhoneNo(String phNo);

}
