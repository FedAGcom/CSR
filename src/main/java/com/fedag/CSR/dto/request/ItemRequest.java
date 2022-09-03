package com.fedag.CSR.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemRequest {

    private String type;

    private String title;

    private String rare;

    private String quality;

    private double price;

}
