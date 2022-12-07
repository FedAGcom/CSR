package com.fedag.CSR.service.impl;

import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Support;
import com.fedag.CSR.repository.SupportRepository;
import com.fedag.CSR.service.SupportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;

    @Override
    public List<Support> findAll() {
        log.info("Все заявки в поддержку найдены");
        return supportRepository.findAll();
    }

    @Override
    public List<Support> findByEmail(String email) {
        log.info("Все заявки в поддержку найдены");
        return supportRepository.findByEmail(email);
    }

    @Override
    public Support findById(Long id) {
        log.info("Поиск сообщения в поддержку по id");
        Support result = supportRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Сообщения в поддержку не найдены");
                    throw new EntityNotFoundException("Сообщения в поддержку не найдены");
                });
        log.info("Сообщение в поддержку по id получено");
        return result;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Удаление сообщения в поддержку с Id: {}", id);
        this.findById(id);
        supportRepository.deleteById(id);
        log.info("Сообщение в поддержку с Id: {} удалено", id);

    }

    @Override
    public Map<String, String> create(Support support) {
        log.info("Создание сообщения в поддержку");
        supportRepository.save(support);
        Map<String, String> message = new HashMap<>();
        message.put("Error", "False");
        message.put("message", "Support message created successfully");
        log.info("Сообщение в поддержку создано");
        return message;
    }
}
