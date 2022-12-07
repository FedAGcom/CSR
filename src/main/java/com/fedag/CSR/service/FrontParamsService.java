package com.fedag.CSR.service;

import com.fedag.CSR.model.FrontParams;

import java.util.Optional;

public interface FrontParamsService {
    FrontParams getFrontParam();
    FrontParams updateFrontParam(FrontParams frontParams);
}
