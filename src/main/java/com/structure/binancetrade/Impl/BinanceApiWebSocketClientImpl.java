package com.structure.binancetrade.Impl;

import com.structure.binancetrade.client.BinanceApiCallback;
import com.structure.binancetrade.client.BinanceApiWebSocketClient;
import com.structure.binancetrade.constant.BinanceApiConstants;
import com.structure.binancetrade.domain.TradeEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.Closeable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BinanceApiWebSocketClientImpl implements BinanceApiWebSocketClient, Closeable {

    private final OkHttpClient client;

    public BinanceApiWebSocketClientImpl(OkHttpClient client) {
        this.client = client;
    }


    @Override
    public Closeable onTradeEvent(String symbols, BinanceApiCallback<TradeEvent> callback) {
        final String channel = Arrays.stream(symbols.split(","))
                .map(String::trim)
                .map(s -> String.format("%s@aggTrade", s))
                .collect(Collectors.joining("/"));
        return createNewWebSocket(channel, new BinanceApiWebSocketListener<>(callback, TradeEvent.class));
    }

    private Closeable createNewWebSocket(String channel, BinanceApiWebSocketListener<?> listener) {
        String streamingUrl = String.format("%s/%s", BinanceApiConstants.WEBSOCKET_BASE_URI, channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        final WebSocket webSocket = client.newWebSocket(request, listener);
        return () -> {
            final int code = 1000;
            listener.onClosing(webSocket, code, null);
            webSocket.close(code, null);
            listener.onClosed(webSocket, code, null);
        };
    }

    @Override
    public void close() {

    }
}
