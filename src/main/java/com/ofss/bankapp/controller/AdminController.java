package com.ofss.bankapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.AccountClosureRequest;
import com.ofss.bankapp.beans.Admin;
import com.ofss.bankapp.beans.SystemSetting;
import com.ofss.bankapp.services.AdminService;

@Controller
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

  private final AdminService service;

  public AdminController(AdminService service) {
    this.service = service;
  }

  @PostMapping("/login")
  @ResponseBody
  public Admin login(@RequestParam String username, @RequestParam String passwordHash) {
    return service.login(username, passwordHash);
  }

  @GetMapping("/closures")
  @ResponseBody
  public List<AccountClosureRequest> pendingClosures() {
    return service.pendingClosures();
  }

  @PutMapping("/closures/{requestId}")
  @ResponseBody
  public AccountClosureRequest process(@RequestParam String username,
                                       @RequestParam String passwordHash,
                                       @PathVariable Long requestId,
                                       @RequestParam String decision) {
    Admin admin = service.login(username, passwordHash);
    return service.processClosure(admin, requestId, decision);
  }

  @PutMapping("/settings")
  @ResponseBody
  public SystemSetting updateSetting(@RequestParam String username,
                                     @RequestParam String passwordHash,
                                     @RequestParam String key,
                                     @RequestParam String value) {
    Admin admin = service.login(username, passwordHash);
    return service.updateSetting(admin, key, value);
  }
  
  @PostMapping
  @ResponseBody
  public Admin create(@RequestBody Admin a) {
      return service.createAdmin(a);
  }
  @GetMapping
  @ResponseBody
  public List<Admin> getAllAdmins() {
      return service.getAllAdmins();
  }


}
