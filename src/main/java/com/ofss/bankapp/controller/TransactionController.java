package com.ofss.bankapp.controller;

import java.math.BigDecimal;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.BillPayment;
import com.ofss.bankapp.beans.TxnTransaction;
import com.ofss.bankapp.services.TransactionService;

@Controller
@RequestMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @PostMapping("/account/{accountId}")
  @ResponseBody
  public TxnTransaction create(@PathVariable Long accountId, @RequestBody TxnTransaction tx) {
    return service.createTransaction(accountId, tx);
  }

  @GetMapping("/{id}")
  @ResponseBody
  public TxnTransaction get(@PathVariable Long id) {
    return service.get(id);
  }

  @PostMapping("/account/{accountId}/bill-payments")
  @ResponseBody
  public BillPayment payBill(@PathVariable Long accountId,
                             @RequestParam BigDecimal amount,
                             @RequestBody BillPayment bill) {
    return service.payBill(accountId, bill, amount);
  }
}
