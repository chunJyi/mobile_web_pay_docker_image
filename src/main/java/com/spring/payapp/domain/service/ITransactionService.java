package com.spring.payapp.domain.service;

import java.util.List;

import com.spring.payapp.exception.TransactionNotFoundException;
import com.spring.payapp.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Transaction;
import com.spring.payapp.domain.entity.TransactionStatus;
import com.spring.payapp.domain.entity.TransactionType;
import com.spring.payapp.domain.entity.dto.TransactionDto;

/**
 * @author chun
 * @package payApp
 * @time 10:58:26 AM
 */
@Service
public interface ITransactionService {

	/**
	 * @param transaction
	 * @return Transaction
	 */
	Transaction saveTransaction(TransactionDto transactionDto);

	/**
	 * @return List<Transaction>
	 */

	List<Transaction> findAll();

	/**
	 * @param userID
	 * @param type
	 * @return List<Transaction>
	 */
	List<Transaction> findByUserIdAndType(int userID, TransactionType type);

	/**
	 * @param id
	 * @return Transaction
	 */
	Transaction findById(int id) throws TransactionNotFoundException;

	/**
	 * @param id
	 */
	void deleteById(int id);

	/**
	 * @param loginUser
	 * @return List<Transaction>
	 */
	List<Transaction> findtransactionsByloginUser(int firstUserID, int secondUserID, TransactionStatus status);

}
