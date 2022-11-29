package com.fedag.CSR.service;

import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.FrontParams;
import com.fedag.CSR.repository.FrontParamsRepository;
import com.fedag.CSR.service.impl.FrontParamsServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith({MockitoExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FrontParamServiceTest {

    @Mock
    FrontParamsRepository frontParamsRepository;
    @InjectMocks
    FrontParamsServiceImpl frontParamsService;

    private FrontParams halloweenFrontParams;

    @BeforeEach
    void init(){
        halloweenFrontParams = FrontParams.builder().id(2L).activeWindow(true).backgroundCase("").backgroundMainBottom("").buttonText("Открыть")
                .colorBackground("#242GS").colorBackgroundOne("#25DGHK").colorBackgroundTwo("#123SDHG").colorButton("#123DF")
                .colorButtons("#AFSN212").colorFooterDown("#FZLFA112").colorFooterUp("#SFDS1LK2J3").colorHeaderLeft("#JFDSKJ1LK23")
                .colorHeaderRight("#JFKJ123").footerLogo("").headerLogo("").textImage("").titleText("Время Нового года!")
                .windowTextTwo("С наступающим праздником новым годом!").build();
    }


    @Test
    @Order(1)
    void getFrontParams(){
        when(frontParamsRepository.findById(1L)).thenReturn(Optional.of(halloweenFrontParams));

        FrontParams existingFrontParams = frontParamsService.getFrontParam();
        assertNotNull(existingFrontParams);
        assertThat(existingFrontParams.getId()).isNotEqualTo(null);
    }


    @Test
    @Order(2)
    void updateFrontParams(){
        when(frontParamsRepository.findById(anyLong())).thenReturn(Optional.of(halloweenFrontParams));

        when(frontParamsRepository.save(any(FrontParams.class))).thenReturn(halloweenFrontParams);

        halloweenFrontParams.setWindowTextTwo("С новым годом!");

        FrontParams existingFrontParams = frontParamsService.updateFrontParam(halloweenFrontParams);

        assertNotNull(existingFrontParams);
        assertEquals("С новым годом!", existingFrontParams.getWindowTextTwo());
    }

    @Test
    @Order(3)
    void getFrontParamsNegative(){
        given(frontParamsRepository.findById(1L)).isNotPresent();
        try {
            frontParamsService.getFrontParam();
        }
        catch (EntityNotFoundException e){
            assertEquals("Фронт параметры не найдены", e.getMessage());
        }
    }

    @Test
    @Order(4)
    void getFrontParamsIfDataIsNotPresentInTable(){
        given(frontParamsRepository.findById(1L)).isNotPresent();
        when(frontParamsRepository.save(any(FrontParams.class))).thenReturn(halloweenFrontParams);
        FrontParams existingFrontParams = frontParamsService.updateFrontParam(halloweenFrontParams);

        assertNotNull(existingFrontParams);
        assertEquals(halloweenFrontParams, existingFrontParams);
    }
}