package com.ofss.bankapp.beans;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ACCOUNT_PREFERENCES")
public class AccountPreference {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PREFERENCE_ID")
  private Long preferenceId;

  @OneToOne
  @JoinColumn(name = "ACCOUNT_ID", unique = true)
  private Account account;

  @Column(name = "ALLOW_INTERNATIONAL_TRANSFERS")
  private String allowInternationalTransfers; // Y/N

  @Column(name = "DAILY_TRANSACTION_LIMIT", precision = 15, scale = 2)
  private BigDecimal dailyTransactionLimit;

  @Column(name = "NOTIFICATION_ENABLED")
  private String notificationEnabled; // Y/N

  @Column(name = "CREATED_AT")
  private OffsetDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private OffsetDateTime updatedAt;

  public AccountPreference() {}

  public Long getPreferenceId() { return preferenceId; }
  public void setPreferenceId(Long preferenceId) { this.preferenceId = preferenceId; }
  public Account getAccount() { return account; }
  public void setAccount(Account account) { this.account = account; }
  public String getAllowInternationalTransfers() { return allowInternationalTransfers; }
  public void setAllowInternationalTransfers(String allowInternationalTransfers) { this.allowInternationalTransfers = allowInternationalTransfers; }
  public BigDecimal getDailyTransactionLimit() { return dailyTransactionLimit; }
  public void setDailyTransactionLimit(BigDecimal dailyTransactionLimit) { this.dailyTransactionLimit = dailyTransactionLimit; }
  public String getNotificationEnabled() { return notificationEnabled; }
  public void setNotificationEnabled(String notificationEnabled) { this.notificationEnabled = notificationEnabled; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
