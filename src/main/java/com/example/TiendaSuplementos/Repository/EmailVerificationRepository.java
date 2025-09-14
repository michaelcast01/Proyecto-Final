package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByVerificationId(String verificationId);
    Optional<EmailVerification> findByEmailAddress(String emailAddress);
}