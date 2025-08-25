package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByAccountNumber(String accountNumber);
}
