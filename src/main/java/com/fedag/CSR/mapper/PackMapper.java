package com.fedag.CSR.mapper;

import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.model.Pack;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PackMapper {

    Page<PackResponse> modelToDto(Page<Pack> packPage);

    PackResponse modelToDto(Pack pack);

    Pack dtoToModel(PackRequest pack);

    Pack dtoToModel(PackUpdate pack);
}
