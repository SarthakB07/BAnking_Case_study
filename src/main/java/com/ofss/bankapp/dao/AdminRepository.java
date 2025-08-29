package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import com.ofss.bankapp.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
  Admin findByUsername(String username);
  
  @Query(value = "SELECT adminsequence.NEXTVAL FROM dual", nativeQuery = true)
  Long nextAdminSeq();

  Optional<Admin> findByAdminNumber(String adminNumber);
}
