package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.BillPayment;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {}
