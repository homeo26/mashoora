package edu.just.mashoora.services;

import edu.just.mashoora.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailVerificationServiceImpl implements EmailVerificationService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String to, Long userId, String token) {
        String subject = "Mashoora Email Verification";
        String text = "Thank you for signing up with Mashoora Platform. Before we can proceed further, we kindly ask you to confirm your email address to activate your account.\n"
                + "\n" + "Please click on the following link to confirm your email address:\n" +
                Constants.ADEL_CHATBOT_API_URL + "?userId=" + userId + "&token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    @Override
    public void sendChangePasswordOTP(String to, Long userId, String OTP) {
        String subject = "Mashoora Change Password OTP";
        String text = "Your OTP is " + userId;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }


}
