package com.kinduberre.trading.schotasticrsicalculator.service;

import com.kinduberre.trading.schotasticrsicalculator.model.StochRsiResponse;

import java.io.IOException;
import java.util.List;

public interface StochRsiService {
    public StochRsiResponse calculateStochRsi(String symbol, String interval,
                                              int lookbackPeriod, int kPeriod, int dPeriod) throws IOException;

}
