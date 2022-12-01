package com.fedag.CSR.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "Предмет", description = "Информация о предмете")
@Builder
public class ItemResponse {
    private BigDecimal id;

    private String type;

    private String title;

    private String rare;

    private String quality;

    private double price;

    private String iconItemId;

    private Double winchance;
}
