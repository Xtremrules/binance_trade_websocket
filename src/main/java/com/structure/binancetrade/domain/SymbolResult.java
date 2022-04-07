package com.structure.binancetrade.domain;

import lombok.Data;

@Data
public class SymbolResult {
    private String symbol;
    private long tradeCount;
    private String medianPrice;
    private String recentPrice;
}