package com.fedag.CSR.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WinChanceId implements Serializable {
    private Long packId;
    private Long itemId;
}
