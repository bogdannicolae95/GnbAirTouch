package com.example.gnbproject.apiService.interfaces;

import com.example.gnbproject.apiService.response.RateResponse;

import java.util.List;

public interface RatesApiCallback {
    void onSuccess(List<RateResponse> responses);
    void onFail(String serverMessage);
}
