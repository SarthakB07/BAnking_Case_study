package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMER_SUPPORT_TICKETS")
public class CustomerSupportTicket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TICKET_ID")
  private Long ticketId;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @Column(name = "SUBJECT")
  private String subject;

  @Lob
  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CREATED_AT")
  private OffsetDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private OffsetDateTime updatedAt;

  public CustomerSupportTicket() {}

  public Long getTicketId() { return ticketId; }
  public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public String getSubject() { return subject; }
  public void setSubject(String subject) { this.subject = subject; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
