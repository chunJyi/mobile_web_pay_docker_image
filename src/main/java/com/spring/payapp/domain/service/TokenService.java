/**
 * 
 */
package com.spring.payapp.domain.service;

import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Token;
import com.spring.payapp.domain.entity.dto.TokenDto;
import com.spring.payapp.domain.repository.ITokenRepo;

import lombok.AllArgsConstructor;

/**
 * @author chun
 * @package payApp
 * @time 2:08:11 PM
 */
@Service
@AllArgsConstructor
public class TokenService implements ITokenService {
	
	private final ITokenRepo tokenRepo;

	@Override
	public void saveToken(TokenDto tokenDto) {
		Token token = Token.builder().account(tokenDto.getAccount()).token(tokenDto.getToken()).expireTime(tokenDto.getExpireTime()).build();
		tokenRepo.save(token);
		
	}

	@Override
	public Token findByToken(String token) {
		return tokenRepo.findByToken(token);
	}
	
	

}
