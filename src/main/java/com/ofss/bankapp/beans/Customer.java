package com.ofss.bankapp.beans;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CUSTOMER_ID")
  private Long customerId;

  @NotBlank
  @Column(name = "FIRST_NAME")
  private String firstName;

  @NotBlank
  @Column(name = "LAST_NAME")
  private String lastName;

  @Email
  @NotBlank
  @Column(name = "EMAIL", unique = true)
  private String email;

  @NotBlank
  @Column(name = "PASSWORD_HASH")
  private String passwordHash;

  @Column(name = "PHONE", unique = true)
  private String phone;

  @Column(name = "DOB")
  private LocalDate dob;

  @Column(name = "GENDER")
  private String gender;

  @Column(name = "ADDRESS_LINE1")
  private String addressLine1;

  @Column(name = "ADDRESS_LINE2")
  private String addressLine2;

  @Column(name = "CITY")
  private String city;

  @Column(name = "STATE")
  private String state;

  @Column(name = "POSTAL_CODE")
  private String postalCode;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "KYC_STATUS")
  private String kycStatus;

  @Column(name = "CREATED_AT")
  private OffsetDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private OffsetDateTime updatedAt;

  public Customer() {}

  // Getters and setters
  public Long getCustomerId() { return customerId; }
  public void setCustomerId(Long customerId) { this.customerId = customerId; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public LocalDate getDob() { return dob; }
  public void setDob(LocalDate dob) { this.dob = dob; }
  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }
  public String getAddressLine1() { return addressLine1; }
  public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
  public String getAddressLine2() { return addressLine2; }
  public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }
  public String getState() { return state; }
  public void setState(String state) { this.state = state; }
  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
  public String getCountry() { return country; }
  public void setCountry(String country) { this.country = country; }
  public String getKycStatus() { return kycStatus; }
  public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
