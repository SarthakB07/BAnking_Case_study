package com.ofss.bankapp.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.ofss.bankapp.beans.BillPayment;

@Service
public class BillPaymentService {

  private final TransactionService transactionService;

  public BillPaymentService(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  public BillPayment pay(Long accountId, BillPayment bill, BigDecimal amount) {
    return transactionService.payBill(accountId, bill, amount);
  }
}
