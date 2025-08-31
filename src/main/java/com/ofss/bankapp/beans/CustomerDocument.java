package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMER_DOCUMENTS")
public class CustomerDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DOCUMENT_ID")
  private Long documentId;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @Column(name = "DOCUMENT_TYPE")
  private String documentType;

  @Column(name = "DOCUMENT_NUMBER")
  private String documentNumber;

  // relative path w.r.t base storage dir
  @Column(name = "DOCUMENT_PATH")
  private String documentPath;

  @Column(name = "UPLOADED_AT")
  private OffsetDateTime uploadedAt;

  @Column(name = "VERIFIED_STATUS")
  private String verifiedStatus;

  // metadata
  @Column(name = "ORIGINAL_FILE_NAME")
  private String originalFileName;

  @Column(name = "CONTENT_TYPE")
  private String contentType;

  @Column(name = "FILE_SIZE")
  private Long fileSize;

  public CustomerDocument() {}

  // --- getters/setters ---
  public Long getDocumentId() { return documentId; }
  public void setDocumentId(Long documentId) { this.documentId = documentId; }
  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public String getDocumentType() { return documentType; }
  public void setDocumentType(String documentType) { this.documentType = documentType; }
  public String getDocumentNumber() { return documentNumber; }
  public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }
  public String getDocumentPath() { return documentPath; }
  public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }
  public OffsetDateTime getUploadedAt() { return uploadedAt; }
  public void setUploadedAt(OffsetDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
  public String getVerifiedStatus() { return verifiedStatus; }
  public void setVerifiedStatus(String verifiedStatus) { this.verifiedStatus = verifiedStatus; }
  public String getOriginalFileName() { return originalFileName; }
  public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }
  public String getContentType() { return contentType; }
  public void setContentType(String contentType) { this.contentType = contentType; }
  public Long getFileSize() { return fileSize; }
  public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
}
