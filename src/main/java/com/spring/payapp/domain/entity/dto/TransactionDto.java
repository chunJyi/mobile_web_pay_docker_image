/**
 * 
 */
package com.spring.payapp.domain.entity.dto;

import java.time.LocalDate;

import com.spring.payapp.domain.entity.TransactionStatus;
import com.spring.payapp.domain.entity.TransactionType;
import com.spring.payapp.domain.entity.Usr;

import lombok.Builder;

/**
 * @author chun
 * @package payApp
 * @time 11:03:06 AM
 */

@Builder
public class TransactionDto {
	
	private int transactionID;
	private Usr sender;
	private Usr receiver;
	private long amount;
	private LocalDate transactionDate;
	private TransactionStatus status;
	private TransactionType type;
	/**
	 * @return the transactionID
	 */
	public int getTransactionID() {
		return transactionID;
	}
	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	/**
	 * @return the sender
	 */
	public Usr getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(Usr sender) {
		this.sender = sender;
	}
	/**
	 * @return the receiver
	 */
	public Usr getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(Usr receiver) {
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
	/**
	 * @return the transactionDate
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the status
	 */
	public TransactionStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	/**
	 * @return the type
	 */
	public TransactionType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TransactionType type) {
		this.type = type;
	}
	
	

}
