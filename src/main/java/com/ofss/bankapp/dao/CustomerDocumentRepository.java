package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.CustomerDocument;

@Repository
public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Long> {}
