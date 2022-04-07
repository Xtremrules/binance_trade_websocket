package com.structure.binancetrade.client;

import com.structure.binancetrade.domain.TradeEvent;

import java.io.Closeable;

/**
 * Binance API data streaming facade, supporting streaming of events through web sockets.
 */
public interface BinanceApiWebSocketClient extends Closeable {



    /**
     * Open a new web socket to receive {@link TradeEvent TradeEvents} on a callback.
     *
     * @param symbols  market (one or coma-separated) symbol(s) to subscribe to
     * @param callback the callback to call on new events
     * @return a {@link Closeable} that allows the underlying web socket to be closed.
     */
    Closeable onTradeEvent(String symbols, BinanceApiCallback<TradeEvent> callback);

    /**
     * @deprecated This method is no longer functional. Please use the returned {@link Closeable} from any of the other methods to close the web socket.
     */
    @Deprecated
    void close();
}
