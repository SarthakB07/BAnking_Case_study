package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ADMINS")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ADMIN_ID")
  private Long adminId;

  @Column(name = "USERNAME", unique = true)
  private String username;

  @Column(name = "PASSWORD_HASH")
  private String passwordHash;

  @Column(name = "FULL_NAME")
  private String fullName;

  @Column(name = "EMAIL", unique = true)
  private String email;

  @Column(name = "PHONE")
  private String phone;

  @Column(name = "ROLE")
  private String role; // ADMIN/SUPERADMIN

  @Column(name = "STATUS")
  private String status; // ACTIVE/SUSPENDED

  @Column(name = "CREATED_AT")
  private OffsetDateTime createdAt;

  @Column(name = "LAST_LOGIN")
  private OffsetDateTime lastLogin;

  public Admin() {}

  public Long getAdminId() { return adminId; }
  public void setAdminId(Long adminId) { this.adminId = adminId; }
  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getLastLogin() { return lastLogin; }
  public void setLastLogin(OffsetDateTime lastLogin) { this.lastLogin = lastLogin; }
}
