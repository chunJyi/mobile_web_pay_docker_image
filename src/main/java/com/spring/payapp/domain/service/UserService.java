/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Usr;
import com.spring.payapp.domain.entity.dto.UserDto;
import com.spring.payapp.domain.repository.IUserRepo;

/**
 * @author chun
 * @package payApp
 * @time 10:37:19 AM
 */
@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepo userRepo;

	@Override
	public Usr findUserByAccount(Account account) {
		return userRepo.findByAccountAccountID(account.getAccountID());
	}

	@Override
	public Usr findUserByPhNo(String PhNo) {
		return userRepo.findByPhoneNo(PhNo);
	}

	@Override
	public  Usr createUser(UserDto userDto) {
		Usr user;
		user = Usr.builder().userID(userDto.getUserID()).userName(userDto.getUserName()).phoneNo(userDto.getPhoneNo()).account(userDto.getAccount())
				.gender(userDto.getGender()).build();
	Usr savedUser =  userRepo.save(user);
	return savedUser;

	}

	/**
	 * 
	 */
	public List<Usr> findUsers() {
		return userRepo.findAll();
		
	}

}
