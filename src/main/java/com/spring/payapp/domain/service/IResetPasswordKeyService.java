/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import com.spring.payapp.domain.entity.ResetPasswordKey;
import com.spring.payapp.domain.entity.dto.TokenDto;

/**
 * @author chun
 * @package payApp
 * @time 6:42:10 PM
 */
public interface IResetPasswordKeyService {

	/**
	 * @param tokenDto
	 */
	void saveKey(TokenDto tokenDto);

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

	/**
	 * @param tokenDto
	 */
	void updateKey(TokenDto tokenDto);

}
