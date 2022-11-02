package com.fedag.CSR.service;

import com.fedag.CSR.model.FrontParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FrontParamsService {

    List<FrontParams> getAllFrontParams();
    FrontParams getTheFrontParamsById(Long id);

    FrontParams createFrontParams(FrontParams frontParams);

    void deleteFrontParams(Long id);
}
