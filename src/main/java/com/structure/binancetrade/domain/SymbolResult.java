package com.structure.binancetrade.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SymbolResult {
    private String symbol;
    private long tradeCount;
    private BigDecimal medianPrice;
    private BigDecimal recentPrice;
}