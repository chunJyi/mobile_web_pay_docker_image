/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Wallet;
import com.spring.payapp.domain.repository.IWalletRepo;

/**
 * @author chun
 * @package payApp
 * @time 9:39:15 AM
 */
@Service
public class WalletService implements IWalletService {

	@Autowired
	private IWalletRepo walletRepo;

	@Override
	public Wallet findByUserID(Integer id) {
		return walletRepo.findByWalletUserUserID(id);

	}

	@Override
	public void save(Wallet wallet) {
		walletRepo.save(wallet);
	}

	@Override
	public List<Wallet> findAll() {
		// TODO Auto-generated method stub
		return walletRepo.findAll();
	}

}
