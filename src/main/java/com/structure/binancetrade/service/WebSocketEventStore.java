package com.structure.binancetrade.service;

import com.structure.binancetrade.domain.TradeEvent;
import com.structure.binancetrade.exception.BinanceApiException;
import com.structure.binancetrade.structure.Map;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Locale;

@Slf4j
public class WebSocketEventStore {

    private final Map<String, ArrayList<TradeEvent>> eventStore;

    public WebSocketEventStore(){
        eventStore = new Map<>();
    }

    public void AddTradeEvent(TradeEvent tradeEvent){
        String key = tradeEvent.getSymbol().toLowerCase(Locale.ROOT);

        if(eventStore.isEmpty())
            eventStore.add(key, new ArrayList<TradeEvent>(){
                {add(tradeEvent);}
            });
        else if(eventStore.get(key) == null)
            eventStore.add(key, new ArrayList<TradeEvent>(){
                {add(tradeEvent);}
            });
        else
            eventStore.get(key).add(tradeEvent);
    }

    public long getCount(String symbol) throws BinanceApiException {
        ArrayList<TradeEvent> tradeEvents = eventStore.get(symbol.toLowerCase(Locale.ROOT));

        if(tradeEvents == null)
            throw new BinanceApiException();

        return tradeEvents.size();
    }

    public ArrayList<TradeEvent> getTradeEvents(String symbol){
        ArrayList<TradeEvent> tradeEvents = eventStore.get(symbol);

        if(tradeEvents == null)
            throw new BinanceApiException();

        return tradeEvents;
    }
}
