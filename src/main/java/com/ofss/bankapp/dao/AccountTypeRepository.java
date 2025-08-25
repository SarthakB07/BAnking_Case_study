package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {}
