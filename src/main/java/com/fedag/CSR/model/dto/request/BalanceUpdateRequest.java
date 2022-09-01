package com.fedag.CSR.model.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class BalanceUpdateRequest {
    private Integer id;
    private int coins;
    private List<Integer> balanceItemsId;
}
