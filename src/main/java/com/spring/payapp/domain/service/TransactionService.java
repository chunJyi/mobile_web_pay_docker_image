/**
 * 
 */
package com.spring.payapp.domain.service;

import java.util.List;

import com.spring.payapp.exception.TransactionNotFoundException;
import com.spring.payapp.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payapp.domain.entity.Transaction;
import com.spring.payapp.domain.entity.TransactionStatus;
import com.spring.payapp.domain.entity.TransactionType;
import com.spring.payapp.domain.entity.dto.TransactionDto;
import com.spring.payapp.domain.repository.ITransactionRepo;

import javax.transaction.TransactionalException;

/**
 * @author chun
 * @package payApp
 * @time 10:58:52 AM
 */
@Service
public class TransactionService implements ITransactionService {

	@Autowired
	private ITransactionRepo transactionRepo;

	@Override
	public Transaction  saveTransaction(TransactionDto transactionDto) {
		
		Transaction transaction=Transaction.builder()
				.transactionID(transactionDto.getTransactionID())
				.sender(transactionDto.getSender())
				.receiver(transactionDto.getReceiver())
				.amount(transactionDto.getAmount())
				.transactionDate(transactionDto.getTransactionDate())
				.status(transactionDto.getStatus())
				.type(transactionDto.getType())
				.build();
		Transaction tran =transactionRepo.save(transaction);
		return tran;
	}

	@Override
	public List<Transaction> findAll() {
	return	transactionRepo.findAll();
		
	}

	@Override
	public List<Transaction> findByUserIdAndType(int userID,TransactionType type) {
		return transactionRepo.findBySenderUserIDAndType(userID,type);
	}

	@Override
	public Transaction findById(int id) throws TransactionNotFoundException {
		return transactionRepo.findById(id).orElseThrow(() -> new TransactionNotFoundException("UserNotFound By " + id));
	}

	@Override
	public List<Transaction> findtransactionsByloginUser(int firstUserID,int secondUserID,TransactionStatus status) {
		return transactionRepo.findBySenderUserIDAndStatusOrReceiverUserIDAndStatus(firstUserID,status,secondUserID,status);
	}

	@Override
	public void deleteById(int id) {
		transactionRepo.deleteById(id);
		
	}

}
