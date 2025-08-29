package com.ofss.bankapp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.Customer;
import com.ofss.bankapp.beans.OtpVerification;
import com.ofss.bankapp.services.CustomerService;
import com.ofss.bankapp.services.OtpService;

@Controller
@RequestMapping(value = "/api/otp", produces = MediaType.APPLICATION_JSON_VALUE)
public class OtpController {

    private final OtpService otpService;
    private final CustomerService customerService;

    public OtpController(OtpService otpService, CustomerService customerService) {
        this.otpService = otpService;
        this.customerService = customerService;
    }

    /**
     * Generate OTP for customer (LOGIN or TRANSACTION)
     */
    @PostMapping("/generate")
    @ResponseBody
    public String generateOtp(@RequestParam Long customerId,
                              @RequestParam String purpose,
                              @RequestParam(required = false) Long referenceId) {
        Customer customer = customerService.get(customerId);
        OtpVerification otp = otpService.generateOtp(customer, purpose, referenceId);
        return "OTP sent successfully with ID: " + otp.getId();
    }

    /**
     * Verify OTP for customer
     */
    @PostMapping("/verify")
    @ResponseBody
    public String verifyOtp(@RequestParam Long customerId,
                            @RequestParam String purpose,
                            @RequestParam String otpCode,
                            @RequestParam(required = false) Long referenceId) {
        Customer customer = customerService.get(customerId);
        boolean verified = otpService.verifyOtp(customer, purpose, otpCode, referenceId);
        return verified ? "✅ OTP verified successfully" : "❌ Invalid or expired OTP";
    }
}
