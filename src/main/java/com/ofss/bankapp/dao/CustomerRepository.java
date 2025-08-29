package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.Customer;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  boolean existsByEmail(String email);
  Customer findByEmail(String email);
  @Query(value = "SELECT custsequence.NEXTVAL FROM dual", nativeQuery = true)
  Long nextCustomerSeq();

  Optional<Customer> findByCustomerNumber(String customerNumber);
}
