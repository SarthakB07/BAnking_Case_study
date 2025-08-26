package com.ofss.bankapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.BillPayment;
import com.ofss.bankapp.beans.TxnTransaction;
import com.ofss.bankapp.services.TransactionService;

@RestController
@RequestMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  // 🔹 Create a new transaction (CREDIT/DEBIT)
  @PostMapping("/account/{accountId}")
  @ResponseBody
  public TxnTransaction create(@PathVariable Long accountId, @RequestBody TxnTransaction tx) {
    return service.createTransaction(accountId, tx);
  }

  // 🔹 Get transaction by ID
  @GetMapping("/{id}")
  @ResponseBody
  public TxnTransaction get(@PathVariable Long id) {
    return service.get(id);
  }

  // 🔹 Pay a bill
  @PostMapping("/account/{accountId}/bill-payments")
  @ResponseBody
  public BillPayment payBill(@PathVariable Long accountId,
                             @RequestParam BigDecimal amount,
                             @RequestBody BillPayment bill) {
    return service.payBill(accountId, bill, amount);
  }

  // 🔹 Get all transactions
  @GetMapping
  //@ResponseBody
  public List<TxnTransaction> getAll() {
    return service.getAll();
  }

  // 🔹 Get all transactions for an account
  @GetMapping("/account/{accountId}")
  @ResponseBody
  public List<TxnTransaction> getByAccount(@PathVariable Long accountId) {
    return service.getByAccount(accountId);
  }

  // 🔹 Delete a transaction
  @DeleteMapping("/{id}")
  @ResponseBody
  public String delete(@PathVariable Long id) {
    service.delete(id);
    return "Transaction " + id + " deleted successfully.";
  }
}
