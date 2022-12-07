package com.fedag.CSR.service.impl;

import com.fedag.CSR.model.FrontParams;
import com.fedag.CSR.repository.FrontParamsRepository;
import com.fedag.CSR.service.FrontParamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class FrontParamsServiceImpl implements FrontParamsService {

    private final FrontParamsRepository frontParamsRepository;

    public FrontParamsServiceImpl(FrontParamsRepository frontParamsRepository) {
        this.frontParamsRepository = frontParamsRepository;
    }

    @Override
    public FrontParams getFrontParam() {
        log.info("Запрос всех FrontParams");
        FrontParams result = frontParamsRepository.findById(1L)
                .orElseThrow(() -> {
                    log.error("Фронт параметры не найдены");
                    throw new EntityNotFoundException("Фронт параметры не найдены");
                });
        log.info("Все FrontParams получены");
        return result;
    }

    @Override
    public FrontParams updateFrontParam(FrontParams frontParams) {
        Optional<FrontParams> optional = frontParamsRepository.findById(1L);

        if (optional.isPresent()) {
            log.info("Обновление фронт параметров");
            FrontParams source = optional.get();
            source.setActiveWindow(frontParams.isActiveWindow());
            source.setBackgroundCase(frontParams.getBackgroundCase());
            source.setBackgroundMainBottom(frontParams.getBackgroundMainBottom());
            source.setButtonText(frontParams.getButtonText());
            source.setColorBackground(frontParams.getColorBackground());
            source.setColorBackgroundOne(frontParams.getColorBackgroundOne());
            source.setColorBackgroundTwo(frontParams.getColorBackgroundTwo());
            source.setColorButton(frontParams.getColorButton());
            source.setColorButtons(frontParams.getColorButtons());
            source.setColorFooterDown(frontParams.getColorFooterDown());
            source.setColorFooterUp(frontParams.getColorFooterUp());
            source.setColorHeaderLeft(frontParams.getColorHeaderLeft());
            source.setColorHeaderRight(frontParams.getColorHeaderRight());
            source.setFooterLogo(frontParams.getFooterLogo());
            source.setHeaderLogo(frontParams.getHeaderLogo());
            source.setTextImage(frontParams.getTextImage());
            source.setTitleText(frontParams.getTitleText());
            source.setWindowTextTwo(frontParams.getWindowTextTwo());
            FrontParams newFrontParams = frontParamsRepository.save(source);
            log.info("Фронт параметры обновлены");
            return newFrontParams;

        } else {
            log.info("Создание фронт параметров");
            FrontParams newFrontParams = frontParamsRepository.save(frontParams);
            log.info("Фронт параметры созданы");
            return newFrontParams;
        }
    }
}
