package com.example.gnbproject.ux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gnbproject.GnbApp;
import com.example.gnbproject.R;
import com.example.gnbproject.domain.Currency;
import com.example.gnbproject.domain.Transaction;
import com.example.gnbproject.ux.Presenter;
import com.example.gnbproject.ux.adaperts.TransactionAdapter;
import com.example.gnbproject.ux.interfaces.ViewInterface;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.Unbinder;

import static android.view.View.VISIBLE;
import static com.example.gnbproject.domain.Currency.*;

public class MainActivity extends AppCompatActivity implements ViewInterface {

    private Unbinder unbinder;
    private Presenter presenter;

    private ArrayAdapter<String> spinnerAdapterForSelectedProduct;
    private ArrayAdapter<String> spinnerAdapterForSelectedCurrency;

    @BindView(R.id.products_spinner)
    Spinner productSpinner;

    @BindView(R.id.select_currency_spinner)
    Spinner selectCurrencySpinner;

    @BindView(R.id.loadingLayout)
    FrameLayout loadingView;

    @BindView(R.id.transaction_recyclerview)
    RecyclerView transactionRecyclerView;

    @BindView(R.id.total_sum_text_view)
    TextView totalAmount;

    Currency currentSelectedCurrency = EUR;
    TransactionAdapter transactionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        presenter = new Presenter(this);
        getLifecycle().addObserver(presenter);

        initViews();

        startGettingProducts();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(presenter);
        unbinder.unbind();
    }

    @Override
    public void showListOfProducts(List<String> products) {
        loadingView.setVisibility(View.GONE);
        spinnerAdapterForSelectedProduct.addAll(products);
    }

    private void initViews(){
        spinnerAdapterForSelectedProduct = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapterForSelectedProduct.setNotifyOnChange(true);
        productSpinner.setAdapter(spinnerAdapterForSelectedProduct);

        String[] stringArray = getResources().getStringArray(R.array.currencies);
        spinnerAdapterForSelectedCurrency = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Arrays.asList(stringArray));
        spinnerAdapterForSelectedCurrency.setNotifyOnChange(true);
        selectCurrencySpinner.setAdapter(spinnerAdapterForSelectedCurrency);

        transactionAdapter = new TransactionAdapter();
        transactionRecyclerView.setAdapter(transactionAdapter);
        transactionRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @OnItemSelected(R.id.select_currency_spinner)
    public void onCurrencySelected(AdapterView<?> parent,View view,int position,long id){
        String selectedCurrency = spinnerAdapterForSelectedCurrency.getItem(position);
        GnbApp.notifyWithToast(selectedCurrency, Toast.LENGTH_SHORT);
        currentSelectedCurrency = valueOf(selectedCurrency);
        presenter.changeSumOfAmount(currentSelectedCurrency);
    }

    @OnItemSelected(R.id.products_spinner)
    public void onProductSelected(AdapterView<?> parent,View view,int position,long id){
        String selectedProductSku = spinnerAdapterForSelectedProduct.getItem(position);
        presenter.getListOfTransactionForProductSku(selectedProductSku,currentSelectedCurrency);
    }

    @Override
    public void showListOfRelatedTransactionAndTotal(List<Transaction> relatedTransactionList, BigDecimal total) {
        transactionAdapter.setListOfTransaction(relatedTransactionList);
        totalAmount.setText(getString(R.string.total_sum,total.doubleValue(),currentSelectedCurrency.toString()));
    }

    @Override
    public void listOfProductsFetchedFail() {
        loadingView.setVisibility(View.GONE);
    }

    public void startGettingProducts(){
        loadingView.setVisibility(VISIBLE);
        presenter.getListOfProducts();
        presenter.getListOfRates();
    }

}

