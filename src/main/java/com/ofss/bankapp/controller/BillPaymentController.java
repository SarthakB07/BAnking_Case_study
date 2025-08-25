package com.ofss.bankapp.controller;

import java.math.BigDecimal;

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

  @PostMapping("/account/{accountId}/pay")
  @ResponseBody
  public BillPayment pay(@PathVariable Long accountId,
                         @RequestParam BigDecimal amount,
                         @RequestBody BillPayment bill) {
    return service.pay(accountId, bill, amount);
  }
}
