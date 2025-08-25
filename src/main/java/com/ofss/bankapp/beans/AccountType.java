package com.ofss.bankapp.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "ACCOUNT_TYPES")
public class AccountType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TYPE_ID")
  private Long typeId;

  @Column(name = "TYPE_NAME", unique = true)
  private String typeName;

  @Column(name = "DESCRIPTION")
  private String description;

  public AccountType() {}

  public Long getTypeId() { return typeId; }
  public void setTypeId(Long typeId) { this.typeId = typeId; }
  public String getTypeName() { return typeName; }
  public void setTypeName(String typeName) { this.typeName = typeName; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
