/**
 * 
 */
package com.spring.payapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payapp.domain.entity.Token;

/**
 * @author chun
 * @package payApp
 * @time 10:19:51 PM
 */

public interface ITokenRepo extends JpaRepository<Token, Long>{

	/**
	 * @param token
	 * @return
	 */
	Token findByToken(String token);

}
