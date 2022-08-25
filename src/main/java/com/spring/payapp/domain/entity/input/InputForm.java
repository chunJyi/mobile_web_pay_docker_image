/**
 * 
 */
package com.spring.payapp.domain.entity.input;

/**
 * @author chun
 * @package payApp
 * @time 3:15:12 PM
 */
public class InputForm {
	
	
	private String to;
	private long amount;
	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}
	/**
	 * @return the amount
	 */
	public long getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	

}
