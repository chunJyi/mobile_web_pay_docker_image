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
import lombok.NoArgsConstructor;

/**
 * @author chun
 * @package com.spring.payapp.domain.entity
 * @time 10:54:01 AM
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Wallet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int walletID;
	private String walletUserID;
	private long amount;
	private LocalDate createDate;
	private LocalDate updateDate;
	@OneToOne
	private Usr walletUser;
	
	
	
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
	 * @return the walledUser
	 */
	public Usr getWalledUser() {
		return walletUser;
	}
	/**
	 * @param walledUser the walledUser to set
	 */
	public void setWalledUser(Usr walledUser) {
		this.walletUser = walledUser;
	}
	/**
	 * @return the walletID
	 */
	public int getWalletID() {
		return walletID;
	}
	/**
	 * @param walletID the walletID to set
	 */
	public void setWalletID(int walletID) {
		this.walletID = walletID;
	}
	/**
	 * @return the walletUserID
	 */
	public String getWalletUserID() {
		return walletUserID;
	}
	/**
	 * @param walletUserID the walletUserID to set
	 */
	public void setWalletUserID(String walletUserID) {
		this.walletUserID = walletUserID;
	}
	/**
	 * @return the createDate
	 */
	public LocalDate getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateDate
	 */
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the walletUser
	 */
	public Usr getWalletUser() {
		return walletUser;
	}
	/**
	 * @param walletUser the walletUser to set
	 */
	public void setWalletUser(Usr walletUser) {
		this.walletUser = walletUser;
	}
	

}
