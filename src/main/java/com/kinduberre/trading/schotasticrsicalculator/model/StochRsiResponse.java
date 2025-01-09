package com.kinduberre.trading.schotasticrsicalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public class StochRsiResponse {
    private String symbol;
    private LocalDateTime timestamp;
    private BigDecimal close;
    private BigDecimal rsi;
    private BigDecimal stochRsi;
    private BigDecimal kLine;
    private BigDecimal dLine;

    public StochRsiResponse(String symbol, LocalDateTime timestamp, BigDecimal close, BigDecimal rsi, BigDecimal stochRsi, BigDecimal kLine, BigDecimal dLine) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.close = close;
        this.rsi = rsi;
        this.stochRsi = stochRsi;
        this.kLine = kLine;
        this.dLine = dLine;
    }

    public StochRsiResponse() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getRsi() {
        return rsi;
    }

    public void setRsi(BigDecimal rsi) {
        this.rsi = rsi;
    }

    public BigDecimal getStochRsi() {
        return stochRsi;
    }

    public void setStochRsi(BigDecimal stochRsi) {
        this.stochRsi = stochRsi;
    }

    public BigDecimal getKLine() {
        return kLine;
    }

    public void setKLine(BigDecimal kLine) {
        this.kLine = kLine;
    }

    public BigDecimal getDLine() {
        return dLine;
    }

    public void setDLine(BigDecimal dLine) {
        this.dLine = dLine;
    }
}