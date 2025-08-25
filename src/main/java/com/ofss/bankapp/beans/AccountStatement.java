package com.ofss.bankapp.beans;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ACCOUNT_STATEMENTS")
public class AccountStatement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "STATEMENT_ID")
  private Long statementId;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID")
  private Account account;

  @Column(name = "STATEMENT_PERIOD_START")
  private LocalDate statementPeriodStart;

  @Column(name = "STATEMENT_PERIOD_END")
  private LocalDate statementPeriodEnd;

  @Column(name = "STATEMENT_FILE_PATH")
  private String statementFilePath;

  @Column(name = "GENERATED_AT")
  private OffsetDateTime generatedAt;

  public AccountStatement() {}

  public Long getStatementId() { return statementId; }
  public void setStatementId(Long statementId) { this.statementId = statementId; }
  public Account getAccount() { return account; }
  public void setAccount(Account account) { this.account = account; }
  public LocalDate getStatementPeriodStart() { return statementPeriodStart; }
  public void setStatementPeriodStart(LocalDate statementPeriodStart) { this.statementPeriodStart = statementPeriodStart; }
  public LocalDate getStatementPeriodEnd() { return statementPeriodEnd; }
  public void setStatementPeriodEnd(LocalDate statementPeriodEnd) { this.statementPeriodEnd = statementPeriodEnd; }
  public String getStatementFilePath() { return statementFilePath; }
  public void setStatementFilePath(String statementFilePath) { this.statementFilePath = statementFilePath; }
  public OffsetDateTime getGeneratedAt() { return generatedAt; }
  public void setGeneratedAt(OffsetDateTime generatedAt) { this.generatedAt = generatedAt; }
}
