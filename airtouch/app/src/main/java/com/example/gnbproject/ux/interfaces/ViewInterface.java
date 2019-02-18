package com.example.gnbproject.ux.interfaces;

import com.example.gnbproject.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ViewInterface {
    void showListOfProducts(List<String> products);
    void showListOfRelatedTransactionAndTotal(List<Transaction> relatedTransactionList, BigDecimal total);
    void listOfProductsFetchedFail();
}
