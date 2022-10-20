package com.fedag.CSR.dto.response;

import com.fedag.CSR.model.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Кейс", description = "Информация о кейсе")
public class PackResponse {
    private BigDecimal id;
    private String title;
    private BigDecimal price;
    private List<ItemResponse> packItemsList;
}

