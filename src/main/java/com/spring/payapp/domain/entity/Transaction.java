/**
 * 
 */
package com.spring.payapp.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author chun
 * @package com.spring.payapp.domain.entity
 * @time 10:57:27 AM
 */
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Transaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionID;
	@OneToOne
	private Usr sender;
	@OneToOne
	private Usr receiver;
	private long amount;
	private LocalDate transactionDate;
	private TransactionStatus status;
	
	private TransactionType type;
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
	 * @param type the type to set
	 */
	public void setType(TransactionType type) {
		this.type = type;
	}
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
	 * @return the from
	 */
	public Usr getSender() {
		return sender;
	}
	/**
	 * @param from the from to set
	 */
	public void setSender(Usr from) {
		this.sender = from;
	}
	/**
	 * @return the to
	 */
	public Usr getReceiver() {
		return receiver;
	}
	/**
	 * @param to the to to set
	 */
	public void setReceiver(Usr to) {
		receiver = to;
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
	 * @return the type
	 */
	public TransactionType getType() {
		return type;
	}
	
	
	
	

}
