package com.example.gnbproject.apiService.interfaces;

import com.example.gnbproject.apiService.response.TransactionResponse;

import java.util.List;

public interface TransactionApiCallback {
    void onSuccess(List<TransactionResponse> response);
    void onFail(String serverResponse);
}
