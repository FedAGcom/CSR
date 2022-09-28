package com.fedag.CSR.repository;


import com.fedag.CSR.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,  BigDecimal> {
    Optional<User> findByUserName(String username);
    Optional<User> findUserByConfirmationToken(String confirmationToken);
    Optional<User> findBySteamId(String steamId);
}
