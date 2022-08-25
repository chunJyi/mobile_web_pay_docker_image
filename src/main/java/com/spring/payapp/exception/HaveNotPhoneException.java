/**
 * 
 */
package com.spring.payapp.exception;

/**
 * @author chun
 * @package payApp
 * @time 8:01:14 AM
 */
public class HaveNotPhoneException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public  HaveNotPhoneException(String message) {
		super(message);
	}

}
