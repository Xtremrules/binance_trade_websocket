package com.structure.binancetrade.constant;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Constants used throughout Binance's API.
 */
public class BinanceApiConstants {

    public static final String WEBSOCKET_BASE_URI = "wss://stream.binance.com:9443/ws";
    public static final String API_GET_SYMBOL_URL = "https://api.binance.com/api/v3/exchangeInfo";

    /**
     * Default ToStringStyle used by toString methods.
     * Override this to change the output format of the overridden toString methods.
     *  - Example ToStringStyle.JSON_STYLE
     */
    public static ToStringStyle TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;
}
