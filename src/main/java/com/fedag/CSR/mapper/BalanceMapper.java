package com.fedag.CSR.mapper;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.dto.request.BalanceRequest;
import com.fedag.CSR.dto.update.BalanceUpdate;
import com.fedag.CSR.dto.response.BalanceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * interface CommentMapper for Dto layer and for class {@link Balance}.
 *
 * @author Kirill Soklakov
 * @since 2022-08-31
 * @version 1.1
 */
public interface BalanceMapper {
    public BalanceResponse modelToDto(Balance balance);

    public List<BalanceResponse> modelToDto(List<Balance> balance);

    public Page<BalanceResponse> modelToDto(Page<Balance> balancePage);

    public Balance dtoToModel(BalanceRequest dto);

    public Balance dtoToModel(BalanceUpdate dto);

    public Balance dtoToModel(BalanceResponse dto);

    public List<Balance> dtoToModel(List<BalanceResponse> dto);

    Balance merge(Balance source, Balance target);
}
