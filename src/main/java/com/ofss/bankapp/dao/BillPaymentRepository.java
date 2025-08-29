package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.ofss.bankapp.beans.BillPayment;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
	 @Query(value = "SELECT bill_seq.NEXTVAL FROM dual", nativeQuery = true)  // Oracle example
	    Long nextBillSeq();
}
