package com.ofss.bankapp.services;

import com.ofss.bankapp.beans.Customer;
import com.ofss.bankapp.beans.OtpVerification;
import com.ofss.bankapp.component.TimeProvider;
import com.ofss.bankapp.dao.OtpVerificationRepository;
import com.ofss.bankapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private final OtpVerificationRepository otpRepo;
    private final TimeProvider clock;
    private final EmailService emailService;

    public OtpService(OtpVerificationRepository otpRepo,
                      TimeProvider clock,
                      EmailService emailService) {
        this.otpRepo = otpRepo;
        this.clock = clock;
        this.emailService = emailService;
    }

    // ✅ Generate OTP
    public OtpVerification generateOtp(Customer customer, String purpose, Long referenceId) {
        String otpCode = String.format("%06d", new Random().nextInt(999999));

        OtpVerification otp = new OtpVerification();
        otp.setCustomer(customer);
        otp.setPurpose(purpose);
        otp.setOtpCode(otpCode);
        otp.setCreatedAt(clock.now());
        otp.setExpiresAt(clock.now().plusMinutes(5));
        otp.setStatus("PENDING");
        otp.setReferenceId(referenceId);

        OtpVerification saved = otpRepo.save(otp);

        // ✅ Send OTP via Email
        emailService.sendOtp(customer.getEmail(), otpCode, purpose);

        return saved;
    }

    // ✅ Verify OTP
    public boolean verifyOtp(Customer customer, String purpose, String otpCode, Long referenceId) {
        return otpRepo.findAll().stream()
                .filter(o -> o.getCustomer().getCustomerId().equals(customer.getCustomerId())
                        && o.getPurpose().equalsIgnoreCase(purpose)
                        && (referenceId == null || referenceId.equals(o.getReferenceId()))
                        && "PENDING".equals(o.getStatus()))
                .findFirst()
                .map(o -> {
                    if (o.getExpiresAt().isBefore(clock.now())) {
                        o.setStatus("EXPIRED");
                        otpRepo.save(o);
                        return false;
                    } else if (o.getOtpCode().equals(otpCode)) {
                        o.setStatus("VERIFIED");
                        otpRepo.save(o);
                        return true;
                    } else {
                        o.setStatus("FAILED");
                        otpRepo.save(o);
                        return false;
                    }
                })
                .orElse(false);
    }
}
