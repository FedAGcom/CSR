package com.fedag.CSR.repository;

import com.fedag.CSR.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long>{

    List<Support> findByEmail(String email);
}
