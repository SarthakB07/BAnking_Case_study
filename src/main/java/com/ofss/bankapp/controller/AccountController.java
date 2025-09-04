package com.ofss.bankapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.Account;
import com.ofss.bankapp.beans.AccountClosureRequest;
import com.ofss.bankapp.beans.AccountPreference;
import com.ofss.bankapp.beans.AccountStatement;
import com.ofss.bankapp.beans.AccountType;
import com.ofss.bankapp.services.AccountService;

@RestController // ✅ better than @Controller + @ResponseBody everywhere
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  // --- Create account for a customer ---
  @PostMapping("/customer/{customerId}")
  public Account open(@PathVariable Long customerId, @RequestBody Account a) {
    return service.openAccount(customerId, a);
  }

  // --- Get all accounts (admin use) ---
  @GetMapping
  public List<Account> getAll() {
    return service.getAll();
  }

  // --- ✅ New: Get accounts by customerId ---
  @GetMapping("/customer/{customerId}")
  public List<Account> getAccountsByCustomer(@PathVariable Long customerId) {
    return service.getAccountsByCustomer(customerId); // ✅ use 'service', not 'accountService'
  }

  // --- Get account by id ---
  @GetMapping("/{id}")
  public Account get(@PathVariable Long id) {
    return service.get(id);
  }

  // --- Update account ---
  @PutMapping("/{id}")
  public Account update(@PathVariable Long id, @RequestBody Account a) {
    return service.update(id, a);
  }

  // --- Delete account ---
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long id) {
    service.delete(id);
    return "Account with ID " + id + " deleted successfully.";
  }

  // --- Preferences ---
  @PutMapping("/{id}/preferences")
  public AccountPreference upsertPref(@PathVariable Long id, @RequestBody AccountPreference p) {
    return service.upsertPreferences(id, p);
  }

  // --- Closure ---
  @PostMapping("/{id}/closure")
  public AccountClosureRequest requestClosure(@PathVariable Long id, @RequestParam String reason) {
    return service.requestClosure(id, reason);
  }

  // --- Statements ---
  @GetMapping("/{id}/statements")
  public List<AccountStatement> statements(@PathVariable Long id) {
    return service.statements(id);
  }

  // --- Account types ---
  @GetMapping("/types")
  public List<AccountType> types() {
    return service.types();
  }
}
