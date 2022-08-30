package com.fedag.balance.repository;


import com.fedag.balance.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Jpa репозиторий модели ballance
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
}
