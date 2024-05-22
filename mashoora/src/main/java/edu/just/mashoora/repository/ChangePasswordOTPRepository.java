package edu.just.mashoora.repository;

import edu.just.mashoora.components.ChangePasswordOTP;
import edu.just.mashoora.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChangePasswordOTPRepository extends JpaRepository<ChangePasswordOTP,Long> {

    @Query("select cpOtp from ChangePasswordOTP cpOtp where cpOtp.otp =?1 and cpOtp.user = ?2")
    ChangePasswordOTP findByOtpAndUser(String otp, User user);


}
