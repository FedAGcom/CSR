package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.mapper.PackMapper;
import com.fedag.CSR.model.Pack;
import com.fedag.CSR.repository.PackRepository;
import com.fedag.CSR.service.PackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepository;
    private final PackMapper packMapper;

    @Override
    public Page<PackResponse> getAllPacks(Pageable pageable) {
        log.info("Получение списка кейсов.");
        Page<PackResponse> result = packMapper.modelToDto(packRepository.findAll(pageable));
        log.info("Список кейсов получен.");
        return result;
    }
    @Override
    public Pack create(PackRequest pack) {
        log.info("Создание кейса.");
        Pack result = packRepository.save(packMapper.dtoToModel(pack));
        log.info("Создание кейса завершено.");
        return result;
    }

    @Override
    public Pack updatePack(PackUpdate pack) {
        log.info("Обновление кейса.");
        Pack result = packRepository.save(packMapper.dtoToModel(pack));
        log.info("Обновление кейса завершено.");
        return result;
    }

    @Override
    public void deletePackById(BigDecimal id) {
        log.info("Удаление кейса.");
        packRepository.deleteById(id);
        log.info("Удаление кейса завершено.");
    }

    @Override
    public PackResponse getPack(BigDecimal id) {
        log.info("Получение кейса по id.");
        PackResponse packResponse = null;
        Optional<Pack> optional = packRepository.findById(id);
        if (optional.isPresent()) {
            Pack pack = optional.get();
            packResponse = packMapper.modelToDto(pack);
        }
        log.info("Получение кейса по id завершено.");
        return packResponse;
    }
    @Override
    public PackResponse getPackByTitle(String title) {
        log.info("Получение кейса по названию.");
        PackResponse packResponse = null;
        Optional<Pack> optional = packRepository.findByTitle(title);
        if (optional.isPresent()) {
            Pack pack = optional.get();
            packResponse = packMapper.modelToDto(pack);
        }
        log.info("Получение кейса по названию завершено.");
        return packResponse;
    }
}
