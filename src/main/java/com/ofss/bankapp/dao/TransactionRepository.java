package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.TxnTransaction;

@Repository
public interface TransactionRepository extends JpaRepository<TxnTransaction, Long> {
	 @Query(value = "SELECT txn_seq.NEXTVAL FROM dual", nativeQuery = true) 
	    Long nextTxnSeq();
}
