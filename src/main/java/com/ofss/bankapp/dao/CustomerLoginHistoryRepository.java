package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.CustomerLoginHistory;

@Repository
public interface CustomerLoginHistoryRepository extends JpaRepository<CustomerLoginHistory, Long> {}
