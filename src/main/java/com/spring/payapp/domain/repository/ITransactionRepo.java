 /**
 * 
 */
package com.spring.payapp.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.payapp.domain.entity.Transaction;
import com.spring.payapp.domain.entity.TransactionStatus;
import com.spring.payapp.domain.entity.TransactionType;

/**
 * @author chun
 * @package payApp
 * @time 3:25:00 PM
 */
public interface ITransactionRepo extends JpaRepository<Transaction, Integer>{

	/**
	 * @param userID
	 */
	List<Transaction> findBySenderUserIDAndType(int userID,TransactionType type);

	/**
	 * @param loginUserID
	 * @return
	 */
	
	List<Transaction> findBySenderUserIDAndStatusOrReceiverUserIDAndStatus(int firstUserID,TransactionStatus statusFir,int secondUserID ,TransactionStatus statusSec);
	
	

}
