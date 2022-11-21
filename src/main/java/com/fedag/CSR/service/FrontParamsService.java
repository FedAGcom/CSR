package com.fedag.CSR.service;

import com.fedag.CSR.model.FrontParams;

import java.util.Optional;

public interface FrontParamsService {
    FrontParams getFrontParam();
    void updateFrontParam(FrontParams frontParams);
}
