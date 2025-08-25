package com.ofss.bankapp.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofss.bankapp.beans.Account;
import com.ofss.bankapp.beans.BillPayment;
import com.ofss.bankapp.beans.TxnTransaction;
import com.ofss.bankapp.component.TimeProvider;
import com.ofss.bankapp.dao.AccountRepository;
import com.ofss.bankapp.dao.BillPaymentRepository;
import com.ofss.bankapp.dao.TransactionRepository;

@Service
public class TransactionService {

  private final TransactionRepository txRepo;
  private final AccountRepository accountRepo;
  private final BillPaymentRepository billRepo;
  private final AccountService accountService;
  private final TimeProvider clock;

  public TransactionService(TransactionRepository txRepo,
                            AccountRepository accountRepo,
                            BillPaymentRepository billRepo,
                            AccountService accountService,
                            TimeProvider clock) {
    this.txRepo = txRepo;
    this.accountRepo = accountRepo;
    this.billRepo = billRepo;
    this.accountService = accountService;
    this.clock = clock;
  }

  @Transactional
  public TxnTransaction createTransaction(Long accountId, TxnTransaction tx) {
    Account a = accountService.get(accountId);
    tx.setAccount(a);
    tx.setInitiatedAt(clock.now());
    tx.setStatus("PENDING");

    if ("DEBIT".equalsIgnoreCase(tx.getTransactionType())) {
      if (a.getBalance() == null) a.setBalance(BigDecimal.ZERO);
      if (a.getBalance().compareTo(tx.getAmount()) < 0) {
        tx.setStatus("FAILED");
        return txRepo.save(tx);
      }
      a.setBalance(a.getBalance().subtract(tx.getAmount()));
    } else if ("CREDIT".equalsIgnoreCase(tx.getTransactionType())) {
      if (a.getBalance() == null) a.setBalance(BigDecimal.ZERO);
      a.setBalance(a.getBalance().add(tx.getAmount()));
    } else {
      tx.setStatus("FAILED");
      return txRepo.save(tx);
    }

    accountRepo.save(a);
    tx.setStatus("SUCCESS");
    tx.setCompletedAt(clock.now());
    return txRepo.save(tx);
  }

  @Transactional
  public BillPayment payBill(Long accountId, BillPayment bill, BigDecimal amount) {
    TxnTransaction tx = new TxnTransaction();
    tx.setTransactionType("DEBIT");
    tx.setAmount(amount);
    tx.setDescription("Bill Payment: " + bill.getBillerName());

    TxnTransaction saved = createTransaction(accountId, tx);
    bill.setTransaction(saved);
    bill.setStatus("SUCCESS".equals(saved.getStatus()) ? "PAID" : "FAILED");
    return billRepo.save(bill);
  }

  public TxnTransaction get(Long id) {
    return txRepo.findById(id).orElseThrow();
  }
}
