package com.example.gnbproject.ux.interfaces;

import com.example.gnbproject.domain.Transaction;

import java.util.List;

public interface PresenterInterface {
    void setListOfTransactions(List<Transaction> listOfTransactions);
    void listOfTransactionFetchedFail();
}
