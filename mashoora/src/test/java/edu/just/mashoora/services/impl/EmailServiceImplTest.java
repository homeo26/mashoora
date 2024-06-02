package edu.just.mashoora.services.impl;

import edu.just.mashoora.constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendVerificationEmail() {
        String to = "test@example.com";
        Long userId = 1L;
        String token = "testToken";

        emailService.sendVerificationEmail(to, userId, token);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage message = messageCaptor.getValue();

        assertEquals(to, message.getTo()[0]);
        assertEquals("Mashoora Email Verification", message.getSubject());
        assertEquals("Thank you for signing up with Mashoora Platform. Before we can proceed further, we kindly ask you to confirm your email address to activate your account.\n\nPlease click on the following link to confirm your email address:\n" +
                Constants.MASHOORA_PROD_EMAIL_VERIFY_API_URL + "?userId=" + userId + "&token=" + token, message.getText());
    }

    @Test
    public void testSendChangePasswordOTP() {
        String to = "test@example.com";
        Long userId = 1L;
        String OTP = "123456";

        emailService.sendChangePasswordOTP(to, userId, OTP);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage message = messageCaptor.getValue();

        assertEquals(to, message.getTo()[0]);
        assertEquals("Mashoora Change Password OTP", message.getSubject());
        assertEquals("Your OTP is " + OTP, message.getText());
    }
}
