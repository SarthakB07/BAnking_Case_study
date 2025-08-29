package com.ofss.bankapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ofss.bankapp.beans.AccountClosureRequest;
import com.ofss.bankapp.beans.Admin;
import com.ofss.bankapp.beans.AdminActionLog;
import com.ofss.bankapp.beans.SystemSetting;
import com.ofss.bankapp.component.TimeProvider;
import com.ofss.bankapp.dao.AccountClosureRequestRepository;
import com.ofss.bankapp.dao.AdminActionLogRepository;
import com.ofss.bankapp.dao.AdminRepository;
import com.ofss.bankapp.dao.SystemSettingRepository;
import com.ofss.bankapp.exception.NotFoundException;

@Service
public class AdminService {

  private final AdminRepository adminRepo;
  private final AdminActionLogRepository logRepo;
  private final SystemSettingRepository settingRepo;
  private final AccountClosureRequestRepository closureRepo;
  private final TimeProvider clock;

  public AdminService(AdminRepository adminRepo,
                      AdminActionLogRepository logRepo,
                      SystemSettingRepository settingRepo,
                      AccountClosureRequestRepository closureRepo,
                      TimeProvider clock) {
    this.adminRepo = adminRepo;
    this.logRepo = logRepo;
    this.settingRepo = settingRepo;
    this.closureRepo = closureRepo;
    this.clock = clock;
  }

  public Admin login(String username, String passwordHash) {
	    Admin a = adminRepo.findByUsername(username);
	    if (a != null && a.getPasswordHash().equals(passwordHash) && !"SUSPENDED".equals(a.getStatus())) {
	        a.setLastLogin(clock.now());
	        adminRepo.save(a);
	        log(a, "LOGIN", "Admin logged in");
	        return a;
	    }
	    throw new NotFoundException("Invalid credentials or admin suspended");
	}

  public void log(Admin admin, String type, String desc) {
    AdminActionLog l = new AdminActionLog();
    l.setAdmin(admin);
    l.setActionType(type);
    l.setActionDescription(desc);
    l.setActionTime(clock.now());
    logRepo.save(l);
  }

  public SystemSetting updateSetting(Admin admin, String key, String value) {
    SystemSetting s = settingRepo.findAll().stream()
      .filter(x -> x.getSettingKey().equals(key))
      .findFirst()
      .orElseGet(() -> {
        SystemSetting ns = new SystemSetting();
        ns.setSettingKey(key);
        return ns;
      });
    s.setSettingValue(value);
    s.setUpdatedByAdmin(admin);
    s.setUpdatedAt(clock.now());
    return settingRepo.save(s);
  }

  public List<AccountClosureRequest> pendingClosures() {
    return closureRepo.findAll().stream()
      .filter(r -> "PENDING".equals(r.getStatus()))
      .toList();
  }

  public AccountClosureRequest processClosure(Admin admin, Long requestId, String decision) {
    AccountClosureRequest r = closureRepo.findById(requestId).orElseThrow();
    r.setStatus(decision);
    r.setProcessedByAdmin(admin);
    r.setProcessedAt(clock.now());
    log(admin, "CLOSURE_" + decision, "Processed closure request " + requestId);
    return closureRepo.save(r);
  }
  public Admin createAdmin(Admin a) {
	    if (a.getAdminNumber() == null || a.getAdminNumber().isBlank()) {
	        Long next = adminRepo.nextAdminSeq();
	        a.setAdminNumber("ADM" + next);
	    }
	    a.setCreatedAt(clock.now());
	    if (a.getStatus() == null) a.setStatus("ACTIVE");
	    return adminRepo.save(a);
	}
  public List<Admin> getAllAdmins() {
	    return adminRepo.findAll();
	}


}
