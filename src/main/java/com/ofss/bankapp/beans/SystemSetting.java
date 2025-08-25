package com.ofss.bankapp.beans;

import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "SYSTEM_SETTINGS")
public class SystemSetting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SETTING_ID")
  private Long settingId;

  @Column(name = "SETTING_KEY", unique = true)
  private String settingKey;

  @Column(name = "SETTING_VALUE")
  private String settingValue;

  @ManyToOne
  @JoinColumn(name = "UPDATED_BY_ADMIN")
  private Admin updatedByAdmin;

  @Column(name = "UPDATED_AT")
  private OffsetDateTime updatedAt;

  public SystemSetting() {}

  public Long getSettingId() { return settingId; }
  public void setSettingId(Long settingId) { this.settingId = settingId; }
  public String getSettingKey() { return settingKey; }
  public void setSettingKey(String settingKey) { this.settingKey = settingKey; }
  public String getSettingValue() { return settingValue; }
  public void setSettingValue(String settingValue) { this.settingValue = settingValue; }
  public Admin getUpdatedByAdmin() { return updatedByAdmin; }
  public void setUpdatedByAdmin(Admin updatedByAdmin) { this.updatedByAdmin = updatedByAdmin; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
