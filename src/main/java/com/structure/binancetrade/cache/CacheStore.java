package com.structure.binancetrade.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheStore<T> {
    private Cache<String, T> cache;

    public CacheStore(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public T get(String key) {
        return cache.getIfPresent(key);
    }

    //Method to put a new record in Cache Store with record key
    public void add(String key, T value) {
        if(key != null && value != null) {
            cache.put(key, value);
            log.info("Record stored in {} cache with Key: {}", value.getClass().getName(), key);
        }
    }

    public Collection<T> getAllValues(){
        return cache.asMap().values();
    }

    public Set<String> getAllKeys(){
        return  cache.asMap().keySet();
    }
}
