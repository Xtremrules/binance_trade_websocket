package com.structure.binancetrade.controller;

import com.structure.binancetrade.cache.CacheStore;
import com.structure.binancetrade.domain.SymbolInfo;
import com.structure.binancetrade.domain.SymbolResult;
import com.structure.binancetrade.service.SymbolDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;

@RestController
public class SymbolController {

    @Autowired
    CacheStore<SymbolInfo> symbolInfoCacheStore;
    @Autowired
    SymbolDataService symbolDataService;

    @GetMapping(value = "/symbols")
    public Collection<SymbolInfo> getAllSymbols() {
        return symbolInfoCacheStore.getAllValues();
    }

    @GetMapping(value = "/symbolkeys")
    public Set<String> getAllSymbolKeys() {
        return symbolInfoCacheStore.getAllKeys();
    }

    @GetMapping(value = "/symbols/{symbolName}")
    public SymbolResult getSymbolData(@PathVariable("symbolName") String symbolName) {
        return symbolDataService.getSymbolData(symbolName);
    }

}
