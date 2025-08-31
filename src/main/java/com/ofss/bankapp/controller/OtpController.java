package com.ofss.bankapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.ofss.bankapp.beans.Customer;
import com.ofss.bankapp.beans.OtpVerification;
import com.ofss.bankapp.beans.OtpVerifyRequest;
import com.ofss.bankapp.beans.TxnTransaction;
import com.ofss.bankapp.services.CustomerService;
import com.ofss.bankapp.services.OtpService;
import com.ofss.bankapp.services.TransactionService;

@RestController   // ✅ Use RestController instead of @Controller + @ResponseBody
@RequestMapping(value = "/api/otp", produces = MediaType.APPLICATION_JSON_VALUE)
public class OtpController {

    private final OtpService otpService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    public OtpController(OtpService otpService,
                         CustomerService customerService,
                         TransactionService transactionService) {
        this.otpService = otpService;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    /**
     * Generate OTP for customer (TRANSACTION only)
     */
    @PostMapping("/generate")
    public String generateOtp(@RequestParam Long customerId,
                              @RequestParam String purpose,
                              @RequestParam(required = false) Long referenceId) {
        Customer customer = customerService.get(customerId);
        OtpVerification otp = otpService.generateOtp(customer, purpose, referenceId);
        return "OTP sent successfully with ID: " + otp.getId();
    }

    /**
     * Verify OTP for transaction
     */
    @PostMapping("/verify")
    public Object verifyOtp(@RequestBody OtpVerifyRequest request) {
        Customer customer = customerService.get(request.getCustomerId());
        boolean verified = otpService.verifyOtp(customer, "TRANSACTION", request.getOtpCode(), request.getTransactionId());

        if (verified) {
            // ✅ Complete the transaction after OTP verification
            TxnTransaction completedTxn = transactionService.completeTransaction(request.getTransactionId());
            return completedTxn; // return full transaction details in JSON
        }

        return "❌ Invalid or expired OTP";
    }
}
