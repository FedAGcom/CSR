package com.fedag.CSR.repository;

import com.fedag.CSR.model.FrontParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FrontParamsRepository extends JpaRepository<FrontParams, Long> {
}
