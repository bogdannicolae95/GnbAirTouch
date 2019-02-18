package com.example.gnbproject;

import com.example.gnbproject.domain.Currency;
import com.example.gnbproject.domain.Rates;
import com.example.gnbproject.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

import static com.example.gnbproject.domain.Currency.EUR;

public class Calculator {


    public static BigDecimal calculateTotalAmount(Transaction transaction, List<Rates> rates, Currency past,Currency selectedCurrency){
        BigDecimal amount = transaction.getAmount();
        Currency current = Currency.valueOf(transaction.getCurrency());
        if(past == null)
            past = Currency.valueOf(transaction.getCurrency());


        if (!current.equals(selectedCurrency)) {
            for (Rates rate : rates) {
                if (rate.getFrom().equals(current) && !past.equals(rate.getTo())){
                    transaction.setAmount(amount.multiply(BigDecimal.valueOf(rate.getRate())));
                    if (rate.getTo().equals(selectedCurrency)) {
                        return amount;
                    }
                    else{
                        transaction.setCurrency(rate.getTo());
                        calculateTotalAmount(transaction, rates, current,selectedCurrency);
                    }
                }
            }
        }
        return amount;
    }

}
