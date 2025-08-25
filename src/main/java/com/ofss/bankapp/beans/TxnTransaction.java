package com.ofss.bankapp.beans;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "TRANSACTIONS")
public class TxnTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TRANSACTION_ID")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID")
  private Account account;

  @Column(name = "RECIPIENT_ACCOUNT_ID")
  private Long recipientAccountId;

  @Column(name = "TRANSACTION_TYPE")
  private String transactionType; // DEBIT/CREDIT

  @Column(name = "AMOUNT", precision = 15, scale = 2)
  private BigDecimal amount;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "STATUS")
  private String status; // PENDING/SUCCESS/FAILED/CANCELLED

  @Column(name = "INITIATED_AT")
  private OffsetDateTime initiatedAt;

  @Column(name = "COMPLETED_AT")
  private OffsetDateTime completedAt;

  public TxnTransaction() {}

  public Long getTransactionId() { return transactionId; }
  public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }
  public Account getAccount() { return account; }
  public void setAccount(Account account) { this.account = account; }
  public Long getRecipientAccountId() { return recipientAccountId; }
  public void setRecipientAccountId(Long recipientAccountId) { this.recipientAccountId = recipientAccountId; }
  public String getTransactionType() { return transactionType; }
  public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getInitiatedAt() { return initiatedAt; }
  public void setInitiatedAt(OffsetDateTime initiatedAt) { this.initiatedAt = initiatedAt; }
  public OffsetDateTime getCompletedAt() { return completedAt; }
  public void setCompletedAt(OffsetDateTime completedAt) { this.completedAt = completedAt; }
}
