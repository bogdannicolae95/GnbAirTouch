package com.example.gnbproject.ux;

import android.widget.Toast;

import com.example.gnbproject.GnbApp;
import com.example.gnbproject.apiService.ApiCallRepository;
import com.example.gnbproject.apiService.interfaces.RatesApiCallback;
import com.example.gnbproject.apiService.interfaces.TransactionApiCallback;
import com.example.gnbproject.apiService.response.RateResponse;
import com.example.gnbproject.apiService.response.TransactionResponse;
import com.example.gnbproject.domain.Currency;
import com.example.gnbproject.domain.Rates;
import com.example.gnbproject.domain.Transaction;
import com.example.gnbproject.ux.interfaces.PresenterInterface;

import java.util.ArrayList;
import java.util.List;

class Model {

    private PresenterInterface presenterInterface;
    private List<Transaction> listOfTransactions;
    private List<Rates> listOfRates;

    Model(PresenterInterface presenterInterface) {
        this.presenterInterface = presenterInterface;
        listOfTransactions = new ArrayList<>();
        listOfRates = new ArrayList<>();
    }

    List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    List<Rates> getListOfRates() {
        return listOfRates;
    }

    void getAllTransactions(){
        new ApiCallRepository().getTransactionsFromServer(new TransactionApiCallback() {
            @Override
            public void onSuccess(List<TransactionResponse> response) {
                for(TransactionResponse t : response){
                    Transaction transaction = new Transaction(t.getSku(),Double.parseDouble(t.getAmount()), Currency.valueOf(t.getCurrency()));
                    listOfTransactions.add(transaction);
                }
                presenterInterface.setListOfTransactions(listOfTransactions);
            }

            @Override
            public void onFail(String serverResponse) {
                if(listOfTransactions != null && !listOfTransactions.isEmpty())
                    presenterInterface.setListOfTransactions(listOfTransactions);
                else
                GnbApp.notifyWithToast(serverResponse, Toast.LENGTH_SHORT);
                presenterInterface.listOfTransactionFetchedFail();
            }
        });
    }

    void getRates(){
        new ApiCallRepository().getRatesFromServer(new RatesApiCallback() {
            @Override
            public void onSuccess(List<RateResponse> responses) {
                for(RateResponse rr : responses){
                    listOfRates.add(new Rates(rr.getFrom(),rr.getTo(),Double.valueOf(rr.getRate())));
                }
            }

            @Override
            public void onFail(String serverMessage) {
                GnbApp.notifyWithToast(serverMessage,Toast.LENGTH_SHORT);
            }
        });
    }

    void clearData() {
        listOfTransactions.clear();
        listOfRates.clear();
    }
}
