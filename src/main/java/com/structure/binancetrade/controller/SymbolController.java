package com.structure.binancetrade.controller;

import com.structure.binancetrade.cache.CacheStore;
import com.structure.binancetrade.domain.SymbolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;

@RestController
public class SymbolController {

    @Autowired
    CacheStore<SymbolInfo> symbolInfoCacheStore;

    @GetMapping(value = "/getAllSymbols")
    public Collection<SymbolInfo> getAllSymbols() {
        return symbolInfoCacheStore.getAllValues();
    }

    @GetMapping(value = "/getKeys")
    public Set<String> getAllSymbolKeys() {
        return symbolInfoCacheStore.getAllKeys();
    }

    @GetMapping(value = "/getSymbols")
    public Collection<SymbolInfo> getSymbolData(@RequestParam String symbolName) {
        return symbolInfoCacheStore.getAllValues();
    }

}
