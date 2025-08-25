package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.AccountClosureRequest;

@Repository
public interface AccountClosureRequestRepository extends JpaRepository<AccountClosureRequest, Long> {}
