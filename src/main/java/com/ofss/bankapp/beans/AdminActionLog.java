package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN_ACTIONS_LOG")
public class AdminActionLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "LOG_ID")
  private Long logId;

  @ManyToOne
  @JoinColumn(name = "ADMIN_ID")
  private Admin admin;

  @Column(name = "ACTION_TYPE")
  private String actionType;

  @Column(name = "ACTION_DESCRIPTION")
  private String actionDescription;

  @Column(name = "ACTION_TIME")
  private OffsetDateTime actionTime;

  @Column(name = "IP_ADDRESS")
  private String ipAddress;

  public AdminActionLog() {}

  public Long getLogId() { return logId; }
  public void setLogId(Long logId) { this.logId = logId; }
  public Admin getAdmin() { return admin; }
  public void setAdmin(Admin admin) { this.admin = admin; }
  public String getActionType() { return actionType; }
  public void setActionType(String actionType) { this.actionType = actionType; }
  public String getActionDescription() { return actionDescription; }
  public void setActionDescription(String actionDescription) { this.actionDescription = actionDescription; }
  public OffsetDateTime getActionTime() { return actionTime; }
  public void setActionTime(OffsetDateTime actionTime) { this.actionTime = actionTime; }
  public String getIpAddress() { return ipAddress; }
  public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}
