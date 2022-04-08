package com.structure.binancetrade.service;

import com.structure.binancetrade.domain.SymbolResult;
import com.structure.binancetrade.domain.TradeEvent;
import com.structure.binancetrade.exception.BinanceApiException;
import com.structure.binancetrade.utils.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SymbolDataService {
    @Autowired
    WebSocketEventStore webSocketEventStore;

    public SymbolResult getSymbolData(String symbol) {
        try {
            symbol = symbol.toLowerCase(Locale.ROOT);
            ArrayList<TradeEvent> tradeEvents = webSocketEventStore.getTradeEvents(symbol);

            SymbolResult result = new SymbolResult();

            int size = tradeEvents.size();

            BigDecimal medianPrice = getMedianPrice(tradeEvents.stream().map(TradeEvent::getPrice).collect(Collectors.toList()));

            result.setSymbol(symbol);
            result.setTradeCount(size);
            result.setRecentPrice(tradeEvents.get(size - 1).getPrice());
            result.setMedianPrice(medianPrice);

            return result;
        } catch (BinanceApiException exception) {
            return new SymbolResult();
        }
    }

    private BigDecimal getMedianPrice(Collection<Number> medianPrices) {
        return Sort.median(medianPrices);
    }
}
