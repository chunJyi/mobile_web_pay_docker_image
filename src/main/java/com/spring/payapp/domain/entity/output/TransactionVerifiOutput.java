/**
 * 
 */
package com.spring.payapp.domain.entity.output;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author chun
 * @package payApp
 * @time 6:44:34 PM
 */
@AllArgsConstructor
@NoArgsConstructor

public class TransactionVerifiOutput {
	
	private String sender;
	private String receiver;
	private long amount;
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
