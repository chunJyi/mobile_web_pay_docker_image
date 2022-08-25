/**
 * 
 */
package com.spring.payapp.domain.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.ResetPasswordKey;
import com.spring.payapp.domain.entity.dto.TokenDto;
import com.spring.payapp.domain.repository.IResetPasswordKeyRepo;

/**
 * @author chun
 * @package payApp
 * @time 6:43:15 PM
 */
@Service
public class ResetPasswordKeyService implements IResetPasswordKeyService{
	
	@Autowired
	private IResetPasswordKeyRepo keyRepo;

	@Override
	public void saveKey(TokenDto tokenDto) {
		ResetPasswordKey key = ResetPasswordKey.builder().account(tokenDto.getAccount()).token(tokenDto.getToken()).expireTime(LocalTime.now()).build();
		keyRepo.save(key);
		
	}
	
	@Override
	public void updateKey(TokenDto tokenDto) {
		ResetPasswordKey key = ResetPasswordKey.builder().account(tokenDto.getAccount()).id(tokenDto.getId()).token(tokenDto.getToken()).expireTime(LocalTime.now()).build();
		keyRepo.save(key);
		
	}

	@Override
	public List<ResetPasswordKey> findByToken(String key) {
		List<ResetPasswordKey> resetPasswordKey=keyRepo.findByToken(key);
		return resetPasswordKey;
	}

	@Override
	public ResetPasswordKey findByAccountAccountID(int accountID) {
		
		return keyRepo.findByAccountAccountID(accountID);
	}

}
