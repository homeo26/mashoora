package edu.just.mashoora.services;

public interface EmailService {
    public void sendVerificationEmail(String to, Long userId, String token);
    public void sendChangePasswordOTP(String to, Long userId, String OTP);
}
