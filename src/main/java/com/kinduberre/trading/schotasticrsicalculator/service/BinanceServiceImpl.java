package com.kinduberre.trading.schotasticrsicalculator.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BinanceServiceImpl implements BinanceService {

    private final RestTemplate restTemplate;

    public BinanceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    private static final String BINANCE_API_URL = "https://api.binance.com/api/v3/klines";

    @Override
    public List<List<Object>> getKlines(String symbol, String interval, Integer limit) {


        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Build the URL with query parameters
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BINANCE_API_URL)
                .queryParam("symbol", symbol)
                .queryParam("interval", interval);

        if (limit != null) {
            uriBuilder.queryParam("limit", limit);
        }

        // Send the GET request and map the response to List<List<Object>>
        return restTemplate.getForObject(uriBuilder.toUriString(), List.class);

    }
}