package edu.just.mashoora.services.impl;

public interface EmailService {
    void sendVerificationEmail(String to, Long userId, String token);
    void sendChangePasswordOTP(String to, Long userId, String OTP);
}
