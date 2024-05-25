package edu.just.mashoora.services;

import edu.just.mashoora.components.ChangePasswordOTP;
import edu.just.mashoora.models.User;
import edu.just.mashoora.repository.ChangePasswordOTPRepository;
import edu.just.mashoora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChangePasswordOTPRepository changePasswordOTPRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    private String generate_OTP(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<6;i++){
            sb.append((int)(Math.random()*10));
        }
        return sb.toString();
    }

    public String generateChangePasswordOtp(User user) {
        changePasswordOTPRepository.findByUser(user).ifPresent(changePasswordOTPRepository::delete);
        String otp = generate_OTP();
        ChangePasswordOTP changePasswordOTP = ChangePasswordOTP.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 120 * 1000))
                .user(user)
                .build();

        changePasswordOTPRepository.save(changePasswordOTP);
        return otp;
    }

    public boolean verifyOTP(String otp, User user) {
        ChangePasswordOTP changePasswordOTP = changePasswordOTPRepository.findByOtpAndUser(otp, user);
        if(changePasswordOTP == null)   return false;
        Date currentTime = new Date();
        if(currentTime.after(changePasswordOTP.getExpirationTime())) return false;

        changePasswordOTPRepository.delete(changePasswordOTP);
        return true;
    }
}
