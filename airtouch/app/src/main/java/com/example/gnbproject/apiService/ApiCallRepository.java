package com.example.gnbproject.apiService;

import com.example.gnbproject.GnbApp;
import com.example.gnbproject.R;
import com.example.gnbproject.apiService.interfaces.RatesApiCallback;
import com.example.gnbproject.apiService.interfaces.TransactionApiCallback;
import com.example.gnbproject.apiService.response.RateResponse;
import com.example.gnbproject.apiService.response.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiCallRepository {

    private ApiCallServices apiCallServices;

    public ApiCallRepository() {
        Retrofit retrofit = new RetrofitConnectionFactory().createConnection(GnbApp.getStringRes(R.string.base_url));
        apiCallServices = retrofit.create(ApiCallServices.class);
    }

    public void getRatesFromServer(final RatesApiCallback ratesApiCallback){
        Callback<List<RateResponse>> callback = new Callback<List<RateResponse>>() {
            @Override
            public void onResponse(Call<List<RateResponse>> call, Response<List<RateResponse>> response) {
                if(response.code() == 200){
                    ratesApiCallback.onSuccess(response.body());
                }else{
                    ratesApiCallback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RateResponse>> call, Throwable t) {
                ratesApiCallback.onFail(t.getMessage());
            }
        };
        apiCallServices.getRatesFromApi().enqueue(callback);
    }

    public void getTransactionsFromServer(final TransactionApiCallback transactionApiCallback){
        Callback<List<TransactionResponse>> callback = new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                if(response.code() == 200){
                    transactionApiCallback.onSuccess(response.body());
                }else{
                    transactionApiCallback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {
                transactionApiCallback.onFail(t.getMessage());
            }
        };
        apiCallServices.getTransactionFromApi().enqueue(callback);
    }
}
