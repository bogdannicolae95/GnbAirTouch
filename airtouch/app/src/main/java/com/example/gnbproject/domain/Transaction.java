package com.example.gnbproject.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class Transaction {
    private String sku;
    private BigDecimal amount;
    private Currency currency;

    public Transaction(String sku, double amount, Currency currency) {
        this.sku = sku;
        this.amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN);
        this.currency = currency;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAmountString() {
        return new DecimalFormat("##.00").format(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency.name();
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(sku, ((Transaction) o).sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}
