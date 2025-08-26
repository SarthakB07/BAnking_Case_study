package com.ofss.bankapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.BillPayment;
import com.ofss.bankapp.services.BillPaymentService;

@Controller
@RequestMapping(value = "/api/bills", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillPaymentController {

  private final BillPaymentService service;

  public BillPaymentController(BillPaymentService service) {
    this.service = service;
  }

  // 1. Pay a bill
  @PostMapping("/account/{accountId}/pay")
  @ResponseBody
  public BillPayment pay(@PathVariable Long accountId,
                         @RequestParam BigDecimal amount,
                         @RequestBody BillPayment bill) {
    return service.pay(accountId, bill, amount);
  }

  // 2. Get all bill payments
  @GetMapping
  @ResponseBody
  public List<BillPayment> getAll() {
    return service.getAll();
  }

  // 3. Get a bill by ID
  @GetMapping("/{id}")
  @ResponseBody
  public BillPayment getById(@PathVariable Long id) {
    return service.getById(id);
  }

  // 4. Get all bills for an account
  @GetMapping("/account/{accountId}")
  @ResponseBody
  public List<BillPayment> getByAccount(@PathVariable Long accountId) {
    return service.getByAccount(accountId);
  }
}
