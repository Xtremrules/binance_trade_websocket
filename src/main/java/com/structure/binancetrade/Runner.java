package com.structure.binancetrade;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.structure.binancetrade.cache.CacheStore;
import com.structure.binancetrade.constant.BinanceApiConstants;
import com.structure.binancetrade.domain.SymbolInfo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Locale;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
    @Autowired
    CacheStore<SymbolInfo> symbolInfoCacheStore;

    OkHttpClient client = new OkHttpClient();


    @Override
    public void run(String... args) throws Exception {
        log.info("This is runner");

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
}
