package com.structure.binancetrade;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.structure.binancetrade.Impl.BinanceApiWebSocketClientImpl;
import com.structure.binancetrade.cache.CacheStore;
import com.structure.binancetrade.client.BinanceApiWebSocketClient;
import com.structure.binancetrade.constant.BinanceApiConstants;
import com.structure.binancetrade.domain.SymbolInfo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
    @Autowired
    CacheStore<SymbolInfo> symbolInfoCacheStore;

    OkHttpClient client = new OkHttpClient();


    @Override
    public void run(String... args) throws Exception {
        log.info("This is runner");

        populateSymbolCacheStore();

        tradeStream();
    }

    private void populateSymbolCacheStore() throws IOException {
        String url = BinanceApiConstants.API_GET_SYMBOL_URL;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(response.body().string());
        JsonNode jsonNode = mapper.readTree(parser);
        JsonNode symbols = jsonNode.get("symbols");

        Iterator<JsonNode> elements = symbols.elements();
        while (elements.hasNext()) {
            JsonNode symbol = elements.next();
            String name = symbol.get("symbol").asText().trim().toLowerCase(Locale.ROOT);
            log.info(name);

            SymbolInfo symbolInfo = new ObjectMapper().readValue(symbol.toString(), SymbolInfo.class);

            log.info(symbolInfo.toString());

            symbolInfoCacheStore.add(name, symbolInfo);
        }
    }

    private void tradeStream(){
        Set<String> pairs = symbolInfoCacheStore.getAllKeys();
        log.info("PAIR SIZE: {}", pairs.size());

        Iterable<List<String>> subSets = Iterables.partition(pairs, 1000);

        final int[] count = {1};

        subSets.forEach(iterator -> {
            log.info("Pair Count: {}", (long) iterator.size());
            String data = String.join(",", iterator);

            BinanceApiWebSocketClient binanceApiWebSocketClient = new BinanceApiWebSocketClientImpl(client);

            int finalCount = count[0];
            binanceApiWebSocketClient.onTradeEvent(data, response -> {
                log.info("Trade {}: {}", finalCount, response.toString());
            });

            count[0]++;
        });
    }
}
