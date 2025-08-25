package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
  Admin findByUsername(String username);
}
