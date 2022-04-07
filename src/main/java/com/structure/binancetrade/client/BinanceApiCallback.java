package com.structure.binancetrade.client;

@FunctionalInterface
public interface BinanceApiCallback<T> {
    void onResponse(T response);


    default void onFailure(Throwable cause) {}
}
