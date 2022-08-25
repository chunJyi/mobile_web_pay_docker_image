/**
 * 
 */
package com.spring.payapp.domain.service;

import com.spring.payapp.domain.entity.Token;
import com.spring.payapp.domain.entity.dto.TokenDto;

/**
 * @author chun
 * @package payApp
 * @time 2:07:20 PM
 */
public interface ITokenService {

	/**
	 * @param tokenDto
	 */
	void saveToken(TokenDto tokenDto);

	/**
	 * @return
	 */
	Token findByToken(String token);

}
