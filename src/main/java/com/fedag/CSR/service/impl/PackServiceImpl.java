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
        return packMapper.modelToDto(packRepository.findAll(pageable));
    }

    @Override
    public Pack create(PackRequest pack) {
        return packRepository.save(packMapper.dtoToModel(pack));
    }

    @Override
    public Pack updatePack(PackUpdate pack) {
        return packRepository.save(packMapper.dtoToModel(pack));
    }

    @Override
    public void deletePackById(BigDecimal id) {
        packRepository.deleteById(id);
    }

    @Override
    public PackResponse getPack(BigDecimal id) {
        PackResponse packResponse = null;
        Optional<Pack> optional = packRepository.findById(id);
        if (optional.isPresent()) {
            Pack pack = optional.get();
            packResponse = packMapper.modelToDto(pack);
        }
        return packResponse;
    }
}
