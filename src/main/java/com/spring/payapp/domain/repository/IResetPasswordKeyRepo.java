/**
 * 
 */
package com.spring.payapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payapp.domain.entity.ResetPasswordKey;

/**
 * @author chun
 * @package payApp
 * @time 6:40:52 PM
 */
public interface IResetPasswordKeyRepo extends JpaRepository<ResetPasswordKey,Long>{

	/**
	 * @param key
	 * @return
	 */
	 List<ResetPasswordKey> findByToken(String key);

	/**
	 * @param accountID
	 * @return
	 */
	ResetPasswordKey findByAccountAccountID(int accountID);

}
