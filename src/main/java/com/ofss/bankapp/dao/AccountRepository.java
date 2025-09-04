package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.Account;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByAccountNumber(String accountNumber);
  List<Account> findByCustomer_CustomerId(Long customerId);
}
