package com.ofss.bankapp.beans;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ACCOUNT_ID")
  private Long accountId;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
  private String accountNumber;

  @Column(name = "ACCOUNT_TYPE")
  private String accountType;

  @Column(name = "BALANCE", precision = 15, scale = 2)
  private BigDecimal balance;

  @Column(name = "CURRENCY")
  private String currency;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "OPENED_AT")
  private OffsetDateTime openedAt;

  @Column(name = "CLOSED_AT")
  private OffsetDateTime closedAt;

  @Column(name = "INTEREST_RATE", precision = 5, scale = 2)
  private BigDecimal interestRate;

  @Column(name = "OVERDRAFT_LIMIT", precision = 15, scale = 2)
  private BigDecimal overdraftLimit;

  public Account() {}

  public Long getAccountId() { return accountId; }
  public void setAccountId(Long accountId) { this.accountId = accountId; }
  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public String getAccountNumber() { return accountNumber; }
  public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
  public String getAccountType() { return accountType; }
  public void setAccountType(String accountType) { this.accountType = accountType; }
  public BigDecimal getBalance() { return balance; }
  public void setBalance(BigDecimal balance) { this.balance = balance; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getOpenedAt() { return openedAt; }
  public void setOpenedAt(OffsetDateTime openedAt) { this.openedAt = openedAt; }
  public OffsetDateTime getClosedAt() { return closedAt; }
  public void setClosedAt(OffsetDateTime closedAt) { this.closedAt = closedAt; }
  public BigDecimal getInterestRate() { return interestRate; }
  public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
  public BigDecimal getOverdraftLimit() { return overdraftLimit; }
  public void setOverdraftLimit(BigDecimal overdraftLimit) { this.overdraftLimit = overdraftLimit; }
}
