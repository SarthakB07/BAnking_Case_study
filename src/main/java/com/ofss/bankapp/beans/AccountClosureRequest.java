package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ACCOUNT_CLOSURE_REQUESTS")
public class AccountClosureRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REQUEST_ID")
  private Long requestId;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID")
  private Account account;

  @Column(name = "REQUEST_DATE")
  private OffsetDateTime requestDate;

  @Column(name = "CLOSURE_REASON")
  private String closureReason;

  @Column(name = "STATUS")
  private String status; // PENDING/APPROVED/REJECTED/COMPLETED

  @ManyToOne
  @JoinColumn(name = "PROCESSED_BY_ADMIN")
  private Admin processedByAdmin;

  @Column(name = "PROCESSED_AT")
  private OffsetDateTime processedAt;

  public AccountClosureRequest() {}

  public Long getRequestId() { return requestId; }
  public void setRequestId(Long requestId) { this.requestId = requestId; }
  public Account getAccount() { return account; }
  public void setAccount(Account account) { this.account = account; }
  public OffsetDateTime getRequestDate() { return requestDate; }
  public void setRequestDate(OffsetDateTime requestDate) { this.requestDate = requestDate; }
  public String getClosureReason() { return closureReason; }
  public void setClosureReason(String closureReason) { this.closureReason = closureReason; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public Admin getProcessedByAdmin() { return processedByAdmin; }
  public void setProcessedByAdmin(Admin processedByAdmin) { this.processedByAdmin = processedByAdmin; }
  public OffsetDateTime getProcessedAt() { return processedAt; }
  public void setProcessedAt(OffsetDateTime processedAt) { this.processedAt = processedAt; }
}
