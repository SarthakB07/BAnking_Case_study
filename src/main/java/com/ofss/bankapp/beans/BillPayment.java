package com.ofss.bankapp.beans;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "BILL_PAYMENTS")
public class BillPayment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BILL_ID")
  private Long billId;

  @OneToOne
  @JoinColumn(name = "TRANSACTION_ID")
  private TxnTransaction transaction;

  @Column(name = "BILLER_NAME")
  private String billerName;

  @Column(name = "BILL_REFERENCE_NUMBER")
  private String billReferenceNumber;

  @Column(name = "DUE_DATE")
  private LocalDate dueDate;

  @Column(name = "STATUS")
  private String status; // PENDING/PAID/FAILED

  public BillPayment() {}

  public Long getBillId() { return billId; }
  public void setBillId(Long billId) { this.billId = billId; }
  public TxnTransaction getTransaction() { return transaction; }
  public void setTransaction(TxnTransaction transaction) { this.transaction = transaction; }
  public String getBillerName() { return billerName; }
  public void setBillerName(String billerName) { this.billerName = billerName; }
  public String getBillReferenceNumber() { return billReferenceNumber; }
  public void setBillReferenceNumber(String billReferenceNumber) { this.billReferenceNumber = billReferenceNumber; }
  public LocalDate getDueDate() { return dueDate; }
  public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
