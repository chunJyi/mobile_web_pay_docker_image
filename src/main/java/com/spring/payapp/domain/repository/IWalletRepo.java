/**
 * 
 */
package com.spring.payapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payapp.domain.entity.Wallet;

/**
 * @author chun
 * @package payApp
 * @time 3:23:56 PM
 */
public interface IWalletRepo extends JpaRepository<Wallet, Integer>{

	/**
	 * @param id
	 */
	Wallet findByWalletUserUserID(Integer id);

}
