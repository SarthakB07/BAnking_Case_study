package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "CUSTOMER_LOGIN_HISTORY")
public class CustomerLoginHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "LOGIN_ID")
  private Long loginId;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID")
  private Customer customer;

  @Column(name = "LOGIN_TIME")
  private OffsetDateTime loginTime;
  
  @Column(name = "STATUS")
  private String status;

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  @Column(name = "IP_ADDRESS")
  private String ipAddress;

  @Column(name = "DEVICE_INFO")
  private String deviceInfo;

  public CustomerLoginHistory() {}

  public Long getLoginId() { return loginId; }
  public void setLoginId(Long loginId) { this.loginId = loginId; }
  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public OffsetDateTime getLoginTime() { return loginTime; }
  public void setLoginTime(OffsetDateTime loginTime) { this.loginTime = loginTime; }
  public String getIpAddress() { return ipAddress; }
  public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
  public String getDeviceInfo() { return deviceInfo; }
  public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }
}
