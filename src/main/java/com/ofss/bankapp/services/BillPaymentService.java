package com.ofss.bankapp.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ofss.bankapp.beans.BillPayment;
import com.ofss.bankapp.dao.BillPaymentRepository;
import com.ofss.bankapp.exception.NotFoundException;

@Service
public class BillPaymentService {

  private final TransactionService transactionService;
  private final BillPaymentRepository billRepo;

  public BillPaymentService(TransactionService transactionService, BillPaymentRepository billRepo) {
    this.transactionService = transactionService;
    this.billRepo = billRepo;
  }

  // Pay a bill (delegates to TransactionService)
  public BillPayment pay(Long accountId, BillPayment bill, BigDecimal amount) {
    BillPayment savedBill = transactionService.payBill(accountId, bill, amount);
    return billRepo.save(savedBill);
  }

  // Get all bill payments
  public List<BillPayment> getAll() {
    return billRepo.findAll();
  }

  // Get bill by ID
  public BillPayment getById(Long id) {
    return billRepo.findById(id)
        .orElseThrow(() -> new NotFoundException("Bill payment not found: " + id));
  }

  // Get bills for a specific account
  public List<BillPayment> getByAccount(Long accountId) {
    return billRepo.findAll().stream()
        .filter(b -> b.getTransaction().getAccount().getAccountId().equals(accountId))
        .toList();
  }
}
