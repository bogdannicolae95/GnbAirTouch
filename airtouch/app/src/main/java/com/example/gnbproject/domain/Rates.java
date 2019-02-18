package com.example.gnbproject.domain;

public class Rates {
    private Currency from;
    private Currency to;
    private double rate;

    public Rates(String from, String to, double rate) {
        this.from = Currency.valueOf(from);
        this.to = Currency.valueOf(to);
        this.rate = rate;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
