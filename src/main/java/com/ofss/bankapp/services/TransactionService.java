package com.ofss.bankapp.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofss.bankapp.beans.Account;
import com.ofss.bankapp.beans.BillPayment;
import com.ofss.bankapp.beans.TxnTransaction;
import com.ofss.bankapp.component.TimeProvider;
import com.ofss.bankapp.dao.AccountRepository;
import com.ofss.bankapp.dao.BillPaymentRepository;
import com.ofss.bankapp.dao.TransactionRepository;
import com.ofss.bankapp.exception.NotFoundException;

@Service
public class TransactionService {

    private final TransactionRepository txRepo;
    private final AccountRepository accountRepo;
    private final BillPaymentRepository billRepo;
    private final AccountService accountService;
    private final TimeProvider clock;
    private final OtpService otpService;   // ✅ Added for OTP

    public TransactionService(TransactionRepository txRepo,
                              AccountRepository accountRepo,
                              BillPaymentRepository billRepo,
                              AccountService accountService,
                              TimeProvider clock,
                              OtpService otpService) {
        this.txRepo = txRepo;
        this.accountRepo = accountRepo;
        this.billRepo = billRepo;
        this.accountService = accountService;
        this.clock = clock;
        this.otpService = otpService;
    }

    // 🔹 Create a transaction (credit/debit)
    @Transactional
    public TxnTransaction createTransaction(Long accountId, TxnTransaction tx) {
        Account a = accountService.get(accountId);

        // Assign transaction number
        if (tx.getTransactionNumber() == null || tx.getTransactionNumber().isBlank()) {
            Long next = txRepo.nextTxnSeq();
            tx.setTransactionNumber("TXN" + next);
        }

        tx.setAccount(a);
        tx.setInitiatedAt(clock.now());

        // ✅ If transaction > 50k → Require OTP
        if (tx.getAmount().compareTo(new BigDecimal("50000")) > 0) {
            tx.setStatus("PENDING_OTP");
            TxnTransaction saved = txRepo.save(tx);

            // Send OTP linked to this transaction
            otpService.generateOtp(a.getCustomer(), "TRANSACTION", saved.getTransactionId());

            return saved; // Stop here until OTP verified
        }

        // ✅ Normal transaction (≤ 50k)
        return processTransaction(a, tx);
    }

    // 🔹 Complete transaction after OTP verification
    @Transactional
    public TxnTransaction completeTransaction(Long txnId) {
        TxnTransaction tx = txRepo.findById(txnId)
                .orElseThrow(() -> new NotFoundException("Transaction not found: " + txnId));

        if (!"PENDING_OTP".equals(tx.getStatus())) {
            throw new IllegalStateException("Transaction is not awaiting OTP verification");
        }	
        
        Account a = tx.getAccount();
        return processTransaction(a, tx);
    }

    // 🔹 Internal transaction logic
    private TxnTransaction processTransaction(Account a, TxnTransaction tx) {
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


    // 🔹 Bill payment
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

    // 🔹 Get transaction by ID
    public TxnTransaction get(Long id) {
        return txRepo.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found: " + id));
    }

    // 🔹 Get all transactions
    public List<TxnTransaction> getAll() {
        return txRepo.findAll();
    }

    // 🔹 Get all transactions for a specific account
    public List<TxnTransaction> getByAccount(Long accountId) {
        return txRepo.findAll().stream()
            .filter(tx -> tx.getAccount().getAccountId().equals(accountId))
            .toList();
    }

    // 🔹 Delete a transaction
    public void delete(Long id) {
        if (!txRepo.existsById(id)) {
            throw new NotFoundException("Transaction not found with id: " + id);
        }
        txRepo.deleteById(id);
    }
}
