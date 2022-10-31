package com.fedag.CSR.service.impl;

import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.FrontParams;
import com.fedag.CSR.repository.FrontParamsRepository;
import com.fedag.CSR.service.FrontParamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class FrontParamsServiceImpl implements FrontParamsService {

    private final FrontParamsRepository frontParamsRepository;

    public FrontParamsServiceImpl(FrontParamsRepository frontParamsRepository) {
        this.frontParamsRepository = frontParamsRepository;
    }

    @Override
    public FrontParams getTheFrontParamsById(Long id) {
        Optional<FrontParams> optional = frontParamsRepository.findById(id);
        if (optional.isPresent())
            return optional.get();

        else
            throw new EntityNotFoundException("There are no such parameters with the id provided.");
    }
    @Override
    public FrontParams createFrontParams(FrontParams frontParams) {
        log.info("Создание FrontParams");
        FrontParams result = frontParamsRepository.save(frontParams);
        log.info("FrontParams созданы");
        return result;
    }

    @Override
    public void deleteFrontParams(Long id) {
        log.info("Удаление FrontParams по id");
        frontParamsRepository.deleteById(id);
        log.info("Удаление FrontParams завершено");
    }
}
