package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.CustomerSupportTicket;

@Repository
public interface CustomerSupportTicketRepository extends JpaRepository<CustomerSupportTicket, Long> {}
