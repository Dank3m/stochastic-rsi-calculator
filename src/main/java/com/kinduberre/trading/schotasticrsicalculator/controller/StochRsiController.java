package com.kinduberre.trading.schotasticrsicalculator.controller;

import com.kinduberre.trading.schotasticrsicalculator.model.StochRsiResponse;
import com.kinduberre.trading.schotasticrsicalculator.service.StochRsiService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;



@RestController
@RequestMapping("/api/indicators")
public class StochRsiController {
    private final StochRsiService stochRsiService;

    public StochRsiController(StochRsiService stochRsiService) {
        this.stochRsiService = stochRsiService;
    }

    @GetMapping("/stoch-rsi")
    public StochRsiResponse getStochRsi(
            @RequestParam String symbol,
            @RequestParam(defaultValue = "1d") String interval,
            @RequestParam(defaultValue = "14") int lookbackPeriod,
            @RequestParam(defaultValue = "3") int kPeriod,
            @RequestParam(defaultValue = "3") int dPeriod) throws IOException {

        return stochRsiService.calculateStochRsi(symbol, interval, lookbackPeriod, kPeriod, dPeriod);
    }
}