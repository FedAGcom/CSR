package com.fedag.CSR.service;

import com.fedag.CSR.model.FrontParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FrontParamsService {
    FrontParams getTheFrontParamsById(Long id);

    FrontParams createFrontParams(FrontParams frontParams);

    void deleteFrontParams(Long id);
}
