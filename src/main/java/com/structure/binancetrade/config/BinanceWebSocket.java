package com.structure.binancetrade.config;

import com.structure.binancetrade.cache.CacheStore;
import com.structure.binancetrade.domain.SymbolInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class BinanceWebSocket {

    @Autowired
    CacheStore<SymbolInfo> symbolInfoCacheStore;


}
