package com.example.gnbproject.apiService;

import com.example.gnbproject.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnectionFactory {

    public Retrofit createConnection(String baseURL){
        return new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
