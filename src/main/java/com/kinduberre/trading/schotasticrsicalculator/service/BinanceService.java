package com.kinduberre.trading.schotasticrsicalculator.service;

import java.io.IOException;
import java.util.List;

public interface BinanceService {

    public List<List<Object>> getKlines(String symbol, String interval, Integer limit) throws IOException;
}
