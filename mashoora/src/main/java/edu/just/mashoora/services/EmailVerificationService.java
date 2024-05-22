package edu.just.mashoora.services;

public interface EmailVerificationService {
    public void sendVerificationEmail(String to, Long userId, String token);
    public void sendChangePasswordOTP(String to, Long userId, String OTP);
}
