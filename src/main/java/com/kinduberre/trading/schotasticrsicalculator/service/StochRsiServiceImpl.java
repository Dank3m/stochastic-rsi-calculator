package com.kinduberre.trading.schotasticrsicalculator.service;

import com.kinduberre.trading.schotasticrsicalculator.model.StochRsiResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class StochRsiServiceImpl implements StochRsiService {

    private final BinanceService binanceService;

    public StochRsiServiceImpl(BinanceService binanceService) {
        this.binanceService = binanceService;
    }
    @Override
    public StochRsiResponse calculateStochRsi(String symbol, String interval,
                                              int lookbackPeriod, int kPeriod, int dPeriod) throws IOException {

        List<List<Object>> klines = binanceService.getKlines(symbol, interval, 1000);
        List<Double> closes = new ArrayList<>();

        // Extract close prices
        for (List<Object> kline : klines) {
            closes.add(Double.parseDouble(kline.get(4).toString()));
            System.out.println("KLine: " + kline);
        }

        // Calculate price changes
        List<Double> gains = new ArrayList<>();
        List<Double> losses = new ArrayList<>();

        for (int i = 1; i < closes.size(); i++) {
            double change = closes.get(i) - closes.get(i - 1);
            gains.add(Math.max(0, change));
            losses.add(Math.max(0, -change));
        }

        // Calculate RSI
        List<Double> avgGains = calculateSMA(gains, lookbackPeriod);
        List<Double> avgLosses = calculateSMA(losses, lookbackPeriod);
        List<Double> rsi = new ArrayList<>();

        for (int i = 0; i < avgGains.size(); i++) {
            Double rs = avgLosses.get(i) == 0 ? 100 : avgGains.get(i) / avgLosses.get(i);
            rsi.add(100 - (100 / (1 + rs)));
        }

        // Calculate Stochastic RSI
        List<Double> stochRsi = new ArrayList<>();
        for (int i = lookbackPeriod; i < rsi.size(); i++) {
            List<Double> rsiPeriod = rsi.subList(i - lookbackPeriod, i);
            Double rsiMin = rsiPeriod.stream().mapToDouble(Double::valueOf).min().orElse(0);
            Double rsiMax = rsiPeriod.stream().mapToDouble(Double::valueOf).max().orElse(100);
            Double stoch = (rsi.get(i) - rsiMin) / (rsiMax - rsiMin);
            stochRsi.add(stoch);
        }

        // Calculate %K and %D lines
        List<Double> kLine = calculateSMA(stochRsi, kPeriod);
        List<Double> dLine = calculateSMA(kLine, dPeriod);

        // Get latest values
        int lastIndex = dLine.size() - 1;
        LocalDateTime timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(klines.get(klines.size() - 1).get(0).toString())),
                ZoneId.systemDefault()
        );

        StochRsiResponse stochRsiResponse = new StochRsiResponse();

        stochRsiResponse.setSymbol(symbol);
        stochRsiResponse.setTimestamp(timestamp);
        stochRsiResponse.setClose(BigDecimal.valueOf(closes.get(closes.size() - 1)));
        stochRsiResponse.setRsi(BigDecimal.valueOf(rsi.get(rsi.size() - 1)));
        stochRsiResponse.setStochRsi(BigDecimal.valueOf(stochRsi.get(stochRsi.size() - 1)));
        stochRsiResponse.setKLine(BigDecimal.valueOf(kLine.get(lastIndex) * 100));
        stochRsiResponse.setDLine(BigDecimal.valueOf(dLine.get(lastIndex) * 100));

        return stochRsiResponse;
    }

    private List<Double> calculateSMA(List<Double> data, int period) {
        List<Double> sma = new ArrayList<>();
        for (int i = period; i <= data.size(); i++) {
            double sum = 0;
            for (int j = i - period; j < i; j++) {
                sum += data.get(j);
            }
            sma.add(sum / period);
        }
        return sma;
    }
}
