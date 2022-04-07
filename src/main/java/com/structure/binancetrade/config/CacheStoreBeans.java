package com.structure.binancetrade.config;

import com.structure.binancetrade.cache.CacheStore;
import com.structure.binancetrade.domain.SymbolInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {
    @Bean
    public CacheStore<SymbolInfo> employeeCache() {
        return new CacheStore<SymbolInfo>(120, TimeUnit.SECONDS);
    }
}
