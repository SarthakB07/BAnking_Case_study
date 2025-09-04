package com.ofss.bankapp.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ofss.bankapp.beans.Account;
import com.ofss.bankapp.beans.AccountClosureRequest;
import com.ofss.bankapp.beans.AccountPreference;
import com.ofss.bankapp.beans.AccountStatement;
import com.ofss.bankapp.beans.AccountType;
import com.ofss.bankapp.component.TimeProvider;
import com.ofss.bankapp.dao.AccountClosureRequestRepository;
import com.ofss.bankapp.dao.AccountPreferenceRepository;
import com.ofss.bankapp.dao.AccountRepository;
import com.ofss.bankapp.dao.AccountStatementRepository;
import com.ofss.bankapp.dao.AccountTypeRepository;
import com.ofss.bankapp.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AccountService {
    @PersistenceContext
    private EntityManager entityManager;

    private final AccountRepository accountRepo;
    private final AccountTypeRepository typeRepo;
    private final AccountPreferenceRepository prefRepo;
    private final AccountStatementRepository stmtRepo;
    private final AccountClosureRequestRepository closureRepo;
    private final CustomerService customerService;
    private final TimeProvider clock;

    public AccountService(AccountRepository accountRepo,
                          AccountTypeRepository typeRepo,
                          AccountPreferenceRepository prefRepo,
                          AccountStatementRepository stmtRepo,
                          AccountClosureRequestRepository closureRepo,
                          CustomerService customerService,
                          TimeProvider clock) {
        this.accountRepo = accountRepo;
        this.typeRepo = typeRepo;
        this.prefRepo = prefRepo;
        this.stmtRepo = stmtRepo;
        this.closureRepo = closureRepo;
        this.customerService = customerService;
        this.clock = clock;
    }

    // ✅ new method
    public List<Account> getAccountsByCustomer(Long customerId) {
        return accountRepo.findByCustomer_CustomerId(customerId);
    }
    // --- Create ---
    public Account openAccount(Long customerId, Account a) {
        a.setCustomer(customerService.get(customerId));
        a.setOpenedAt(clock.now());
        if (a.getBalance() == null) a.setBalance(BigDecimal.ZERO);
        if (a.getStatus() == null) a.setStatus("ACTIVE");

        // 🔹 Generate account number from sequence
        Long nextVal = ((Number) entityManager
                .createNativeQuery("SELECT accsequence.NEXTVAL FROM dual")
                .getSingleResult())
                .longValue();
        a.setAccountNumber("ACC" + nextVal);

        return accountRepo.save(a);
    }

    // --- Read ---
    public Account get(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found: " + id));
    }

    public List<Account> getAll() {
        return accountRepo.findAll();
    }

    // --- Update ---
    public Account update(Long id, Account upd) {
        Account a = get(id);
        if (upd.getAccountType() != null) a.setAccountType(upd.getAccountType());
        if (upd.getStatus() != null) a.setStatus(upd.getStatus());
        if (upd.getCurrency() != null) a.setCurrency(upd.getCurrency());
        if (upd.getOverdraftLimit() != null) a.setOverdraftLimit(upd.getOverdraftLimit());
        a.setBalance(upd.getBalance() != null ? upd.getBalance() : a.getBalance());
        a.setInterestRate(upd.getInterestRate() != null ? upd.getInterestRate() : a.getInterestRate());
        return accountRepo.save(a);
    }

    // --- Delete ---
    public void delete(Long id) {
        if (!accountRepo.existsById(id)) {
            throw new NotFoundException("Account not found: " + id);
        }
        accountRepo.deleteById(id);
    }

    // --- Preferences ---
    public AccountPreference upsertPreferences(Long accountId, AccountPreference p) {
        p.setAccount(get(accountId));
        if (p.getCreatedAt() == null) p.setCreatedAt(clock.now());
        p.setUpdatedAt(clock.now());
        return prefRepo.save(p);
    }

    // --- Closure ---
    public AccountClosureRequest requestClosure(Long accountId, String reason) {
        AccountClosureRequest r = new AccountClosureRequest();
        r.setAccount(get(accountId));
        r.setRequestDate(clock.now());
        r.setClosureReason(reason);
        r.setStatus("PENDING");
        return closureRepo.save(r);
    }

    // --- Statements ---
    public List<AccountStatement> statements(Long accountId) {
        return stmtRepo.findAll().stream()
                .filter(s -> s.getAccount().getAccountId().equals(accountId))
                .toList();
    }

    // --- Types ---
    public List<AccountType> types() {
        return typeRepo.findAll();
    }
}
