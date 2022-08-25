/**
 * 
 */
package com.spring.payapp.controller;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.spring.payapp.exception.TransactionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.payapp.domain.entity.Account;
import com.spring.payapp.domain.entity.Transaction;
import com.spring.payapp.domain.entity.TransactionStatus;
import com.spring.payapp.domain.entity.TransactionType;
import com.spring.payapp.domain.entity.Usr;
import com.spring.payapp.domain.entity.Wallet;
import com.spring.payapp.domain.entity.dto.TransactionDto;
import com.spring.payapp.domain.entity.input.InputForm;
import com.spring.payapp.domain.entity.output.TransactionSuccessOutput;
import com.spring.payapp.domain.entity.output.TransactionVerifiOutput;
import com.spring.payapp.domain.service.IAccountService;
import com.spring.payapp.domain.service.ITransactionService;
import com.spring.payapp.domain.service.IUserService;
import com.spring.payapp.domain.service.IWalletService;
import com.spring.payapp.mailUtil.MailHelper;

/**
 * @author chun
 * @package payApp
 * @time 10:55:25 AM
 */
@Controller
public class TransactionController {


	private final IAccountService accountService;
	private final IUserService userService;

	private final IWalletService walletService;

	private final ITransactionService transactionService;
	private final  MailHelper mailHelper;


	private TransactionDto transactionDto;

	public TransactionController(IAccountService accountService, IUserService userService, IWalletService walletService, ITransactionService transactionService, MailHelper mailHelper) {
		this.accountService = accountService;
		this.userService = userService;
		this.walletService = walletService;
		this.transactionService = transactionService;
		this.mailHelper = mailHelper;
	}

	@PostMapping("/user/sendRequest")
	public String sendFormRequestHomePage(@ModelAttribute(name = "inputForm") @Valid InputForm inputForm,
		RedirectAttributes redirectAttributes, BindingResult result) {
		String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		Usr loginUser = userService.findUserByAccount(loginAccount);
		Usr requestTo = userService.findUserByPhNo(inputForm.getTo());
		
		if (requestTo != null && loginUser.getUserID() != requestTo.getUserID()) {
			TransactionDto dto = TransactionDto.builder().sender(requestTo).receiver(loginUser).amount(inputForm.getAmount())
					.transactionDate(LocalDate.now()).status(TransactionStatus.Pending).type(TransactionType.Request)
					.build();
			transactionService.saveTransaction(dto);
		}
		redirectAttributes.addFlashAttribute("message","Can't send Yourself!");
		return "redirect:/";
	}

	@PostMapping("/user/send")
	@Transactional
	public String sendFormHomePage(@ModelAttribute(name = "inputForm") @Valid InputForm inputForm,
			RedirectAttributes redirectAttributes, BindingResult result) {
		Transaction t = sendFromHomePage(inputForm);
		if (t!=null) {
			return requestToFriendReuqest(t.getTransactionID(),redirectAttributes);
		}
		redirectAttributes.addFlashAttribute("message","User not found!");

		return "redirect:/";
	}

	public Transaction sendFromHomePage(InputForm inputForm) {
		String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		Usr loginUser = userService.findUserByAccount(loginAccount);
		Usr receiver = userService.findUserByPhNo(inputForm.getTo());
		if (loginUser.getUserID() != receiver.getUserID()) {
			TransactionDto dto = TransactionDto.builder().sender(loginUser).receiver(receiver).amount(inputForm.getAmount())
					.transactionDate(LocalDate.now()).status(TransactionStatus.Pending).type(TransactionType.Request)
					.build();

			Transaction transaction = transactionService.saveTransaction(dto);
			return transaction;
		}
		
		return null;
	}

	@GetMapping("/requestApprove/{id}")
	public String requestToFriendReuqest(@PathVariable(name = "id") int id,RedirectAttributes redirectAttributes) {
		Transaction transaction = null;
		try {
			transaction = transactionService.findById(id);
		} catch (TransactionNotFoundException e) {
			e.printStackTrace();
		}
		int userID=transaction.getSender().getUserID();
		Wallet wallet=walletService.findByUserID(userID);
		if (wallet.getAmount() >= transaction.getAmount()+1000) {
			this.transactionDto = TransactionDto.builder().transactionID(transaction.getTransactionID())
					.sender(transaction.getSender()).receiver(transaction.getReceiver()).amount(transaction.getAmount())
					.transactionDate(LocalDate.now()).status(TransactionStatus.Success).type(TransactionType.Send).build();
			userService.findUsers();
			return "redirect:/transactionVerification";
		}
		return requestReject(id,redirectAttributes);
		
	}
	@GetMapping("/requestReject/{id}")
	public String requestReject(@PathVariable(name = "id") int id,RedirectAttributes redirectAttributes) {
		Transaction transaction = null;
		try {
			transaction = transactionService.findById(id);
		} catch (TransactionNotFoundException e) {
			return "redirect:/";
		}
		transactionService.deleteById(transaction.getTransactionID());
		redirectAttributes.addFlashAttribute("message","Rejected this request!");
		return "redirect:/";
		
	}

	@GetMapping("/transactionVerification")
	public String transactionVeifi(ModelMap map) {

		TransactionVerifiOutput output = new TransactionVerifiOutput(transactionDto.getSender().getUserName(),
				transactionDto.getReceiver().getUserName(), transactionDto.getAmount());
		map.addAttribute("outputForm", output);
		return "app-transaction-verification";
	}

	@GetMapping("/transactionVerification/cancel")
	public String transactionVeifiCanecl(ModelMap map) {
		this.transactionDto = null;
		return "redirect:/";

	}

	@GetMapping("/transactionVerification/confirm")
	@Transactional
	public String transactionVeifiConfirm(ModelMap map) {
		transactionService.saveTransaction(transactionDto);

		TransactionSuccessOutput output = new TransactionSuccessOutput();
		output.setStatus(transactionDto.getStatus().name());
		output.setTo(transactionDto.getReceiver().getUserName());
		output.setFrom(transactionDto.getSender().getUserName());
		output.setAmount(transactionDto.getAmount());
		output.setDate(transactionDto.getTransactionDate().toString());
		map.addAttribute("output", output);
		removeAmountSender(transactionDto);
		addAmountReceiver(transactionDto);
		transactionService.saveTransaction(transactionDto);
		
		String details =transactionDto.getSender().getUserName()+ " have a transaction (amount = "+ transactionDto.getAmount() + " to " +transactionDto.getSender().getUserName()+")"
				;
		mailHelper.sendMail(transactionDto.getReceiver().getAccount().getEmail(), "Notification for Transaction.",details);
		this.transactionDto = null;
		return "app-transaction-detail";

	}

	public void removeAmountSender(TransactionDto transactionDto) {
		Wallet wallet = walletService.findByUserID(transactionDto.getSender().getUserID());
		long result = wallet.getAmount() - transactionDto.getAmount();
		wallet.setAmount(result);
		walletService.save(wallet);

	}

	public void addAmountReceiver(TransactionDto transactionDto) {
		Wallet wallet = walletService.findByUserID(transactionDto.getReceiver().getUserID());
		long result = wallet.getAmount() + transactionDto.getAmount();
		wallet.setAmount(result);
		walletService.save(wallet);

	}
	
	@GetMapping("/user/transactionLists")
	public String transactionList(ModelMap map) {
		Usr loginUser = null;
		String loginEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Account loginAccount = accountService.findAccountByEmail(loginEmail);
		loginUser = userService.findUserByAccount(loginAccount);
		List<Transaction> transactions = transactionService.findtransactionsByloginUser(loginUser.getUserID(),
				loginUser.getUserID(), TransactionStatus.Success);
		Wallet wallet = walletService.findByUserID(loginUser.getUserID());
		map.addAttribute("wallet", wallet);
		map.addAttribute("transactions", transactions);
		return "app-transactions";
		
	}

}
