/**
 * 
 */
package com.spring.payapp.exception;

/**
 * @author chun
 * @package payApp
 * @time 2:03:10 PM
 */
public class UserNotFoundException extends Throwable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

}
