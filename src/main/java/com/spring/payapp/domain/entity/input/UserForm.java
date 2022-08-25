/**
 * 
 */
package com.spring.payapp.domain.entity.input;

import com.spring.payapp.domain.entity.Gender;

/**
 * @author chun
 * @package payApp
 * @time 4:07:39 PM
 */
public class UserForm {
	
	private String username;
	private String phNo;
	private Gender gender;
	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the phNo
	 */
	public String getPhNo() {
		return phNo;
	}
	/**
	 * @param phNo the phNo to set
	 */
	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}
	
	

}
