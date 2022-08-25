/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Usr;
import com.spring.payapp.domain.entity.dto.UserDto;

/**
 * @author chun
 * @package payApp
 * @time 10:40:06 AM
 */
@Service
@Configuration
public interface IUserService {

	/**
	 * @param account
	 * @return
	 */
	Usr findUserByAccount(Account account);

	/**
	 * @param PhNo
	 * @return
	 */
	Usr findUserByPhNo(String PhNo);

	/**
	 * @param userDto
	 */
	Usr createUser(UserDto userDto);

	/**
	 * @return
	 */
	List<Usr> findUsers();

}
