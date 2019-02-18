package com.example.gnbproject.ux;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.widget.Toast;

import com.example.gnbproject.Calculator;
import com.example.gnbproject.GnbApp;
import com.example.gnbproject.domain.Currency;
import com.example.gnbproject.domain.Rates;
import com.example.gnbproject.domain.Transaction;
import com.example.gnbproject.ux.interfaces.PresenterInterface;
import com.example.gnbproject.ux.interfaces.ViewInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Presenter implements PresenterInterface,LifecycleObserver {

    private ViewInterface viewInterface;
    private Model model;
    private List<Transaction> relatedTransactionList;


    public Presenter(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
        this.model = new Model(this);
        relatedTransactionList = new ArrayList<>();
    }



    @Override
    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        List<String> products = new ArrayList<>();

        //first solution to eliminate duplicates from product list
        for(Transaction tr : listOfTransactions){
            products.add(tr.getSku());
        }
        Set<String> setAux = new HashSet<>(products);
        products.clear();
        products.addAll(setAux);

        products.sort(Comparator.naturalOrder());

        //second solution to eliminate duplicates;
//        for (Transaction transaction : listOfTransactions){
//            if(!products.contains(transaction.getSku()))
//            products.add(transaction.getSku());
//        }

        viewInterface.showListOfProducts(products);
    }

    @Override
    public void listOfTransactionFetchedFail() {
        viewInterface.listOfProductsFetchedFail();
    }

    public void getListOfTransactionForProductSku(String selectedProductSku, Currency toCurrencySelected) {
            relatedTransactionList.clear();
            for (Transaction tr : model.getListOfTransactions()) {
                if (tr.getSku().equals(selectedProductSku))
                    relatedTransactionList.add(tr);
            }
            GnbApp.notifyWithToast(selectedProductSku, Toast.LENGTH_SHORT);

            BigDecimal total = calculateTotalInSelectedCurrency(relatedTransactionList, toCurrencySelected);
            viewInterface.showListOfRelatedTransactionAndTotal(relatedTransactionList, total);

    }

    public void changeSumOfAmount(Currency currentSelectedCurrency) {
        BigDecimal total = calculateTotalInSelectedCurrency(relatedTransactionList, currentSelectedCurrency);
        viewInterface.showListOfRelatedTransactionAndTotal(relatedTransactionList, total);
    }

    private BigDecimal calculateTotalInSelectedCurrency(List<Transaction> relatedTransactionList,Currency toCurrencySelected){
        BigDecimal total = BigDecimal.ZERO;
        List<Rates> rates = model.getListOfRates();
        if(rates!=null){
            for(Transaction transaction : relatedTransactionList){
                total = total.add(Calculator.calculateTotalAmount(transaction,rates,null,toCurrencySelected));
            }
        }
        return total;
    }

    public void getListOfProducts() {
        model.getAllTransactions();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clearData(){
        model.clearData();
    }

    public void getListOfRates() {
        model.getRates();
    }
}
