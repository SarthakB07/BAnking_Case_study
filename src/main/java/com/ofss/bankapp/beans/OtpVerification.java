package com.ofss.bankapp.beans;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "OTP_VERIFICATIONS")
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String purpose; // LOGIN / TRANSACTION
    private String otpCode;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
    private String status; // PENDING, VERIFIED, EXPIRED

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private Long referenceId; 
    // e.g. transactionId (for transactions), null for login

    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(OffsetDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    // ---------- toString ----------
    @Override
    public String toString() {
        return "OtpVerification{" +
                "id=" + id +
                ", purpose='" + purpose + '\'' +
                ", otpCode='" + otpCode + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", status='" + status + '\'' +
                ", customerId=" + (customer != null ? customer.getCustomerId() : null) +
                ", referenceId=" + referenceId +
                '}';
    }
}
