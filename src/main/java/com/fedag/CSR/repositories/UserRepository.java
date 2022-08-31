package com.fedag.CSR.repositories;


import com.fedag.CSR.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
