package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ofss.bankapp.beans.OtpVerification;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
}
