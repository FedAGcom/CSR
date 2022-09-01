package com.fedag.CSR.model.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class BalanceResponse {

    private Integer id;
    private int coins;
    private List<Integer> balanceItemsId;
}
