package com.ofss.bankapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.Account;
import com.ofss.bankapp.beans.AccountClosureRequest;
import com.ofss.bankapp.beans.AccountPreference;
import com.ofss.bankapp.beans.AccountStatement;
import com.ofss.bankapp.beans.AccountType;
import com.ofss.bankapp.services.AccountService;

@Controller
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  // --- CRUD ---
  @PostMapping("/customer/{customerId}")
  @ResponseBody
  public Account open(@PathVariable Long customerId, @RequestBody Account a) {
    return service.openAccount(customerId, a);
  }

  @GetMapping
  @ResponseBody
  public List<Account> getAll() {
    return service.getAll();
  }

  // account id -> id
  @GetMapping("/{id}")
  @ResponseBody
  public Account get(@PathVariable Long id) {
    return service.get(id);
  }

  @PutMapping("/{id}")
  @ResponseBody
  public Account update(@PathVariable Long id, @RequestBody Account a) {
    return service.update(id, a);
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public String delete(@PathVariable Long id) {
    service.delete(id);
    return "Account with ID " + id + " deleted successfully.";
  }

  // --- Preferences ---
  // here also it is account id
  @PutMapping("/{id}/preferences")
  @ResponseBody
  public AccountPreference upsertPref(@PathVariable Long id, @RequestBody AccountPreference p) {
    return service.upsertPreferences(id, p);
  }

  // --- Closure ---
  @PostMapping("/{id}/closure")
  @ResponseBody
  public AccountClosureRequest requestClosure(@PathVariable Long id, @RequestParam String reason) {
    return service.requestClosure(id, reason);
  }

  // --- Statements ---
  @GetMapping("/{id}/statements")
  @ResponseBody
  public List<AccountStatement> statements(@PathVariable Long id) {
    return service.statements(id);
  }

  // --- Types ---
  @GetMapping("/types")
  @ResponseBody
  public List<AccountType> types() {
    return service.types();
  }
}
