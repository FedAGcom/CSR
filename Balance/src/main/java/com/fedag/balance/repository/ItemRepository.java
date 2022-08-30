package com.fedag.balance.repository;

import com.fedag.balance.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Jpa репозиторий для модели Item, для тестирования кода
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
