/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Wallet;

/**
 * @author chun
 * @package payApp
 * @time 9:38:28 AM
 */
@Service
public interface IWalletService {

	/**
	 * @param id
	 * @return
	 */
	Wallet findByUserID(Integer id);

	/**
	 * @param wallet
	 */
	void save(Wallet wallet);

	/**
	 * @return
	 */
	List<Wallet> findAll();

}
