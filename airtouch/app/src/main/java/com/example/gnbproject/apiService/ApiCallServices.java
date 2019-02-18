package com.example.gnbproject.apiService;

import com.example.gnbproject.apiService.response.RateResponse;
import com.example.gnbproject.apiService.response.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCallServices {

    @GET("/rates.json")
    Call<List<RateResponse>> getRatesFromApi();

    @GET("/transactions.json")
    Call<List<TransactionResponse>> getTransactionFromApi();

}
