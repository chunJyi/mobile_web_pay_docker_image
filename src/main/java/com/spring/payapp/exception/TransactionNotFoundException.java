/**
 * 
 */
package com.spring.payapp.exception;

/**
 * @author chun
 * @package payApp
 * @time 2:03:10 PM
 */
public class TransactionNotFoundException extends Throwable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public TransactionNotFoundException(String message) {
		super(message);
	}

}
