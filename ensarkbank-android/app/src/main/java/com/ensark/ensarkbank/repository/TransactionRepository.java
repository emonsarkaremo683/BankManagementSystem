package com.ensark.ensarkbank.repository;

import android.content.Context;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.TransactionApiService;
import com.ensark.ensarkbank.model.dto.AccountTransactionRequest;
import com.ensark.ensarkbank.model.dto.AccountTransactionResponse;
import com.ensark.ensarkbank.model.dto.ATMTransactionRequest;
import com.ensark.ensarkbank.model.dto.ATMTransactionResponse;
import com.ensark.ensarkbank.model.dto.BalanceCheckRequest;
import com.ensark.ensarkbank.model.dto.JournalResponse;
import com.ensark.ensarkbank.model.dto.OtpInitiateResponse;
import com.ensark.ensarkbank.model.dto.OtpVerifyRequest;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class TransactionRepository {

    private final TransactionApiService apiService;

    public TransactionRepository(Context context) {
        apiService = ApiClient.getClient(context).create(TransactionApiService.class);
    }

    public void initiateOnlineTransaction(AccountTransactionRequest atr, Callback<OtpInitiateResponse> callback) {
        apiService.initiateOnlineTransaction(atr).enqueue(callback);
    }

    public void verifyOnlineTransaction(OtpVerifyRequest req, Callback<AccountTransactionResponse> callback) {
        apiService.verifyOnlineTransaction(req).enqueue(callback);
    }

    public void getTransactionById(Long id, Callback<AccountTransactionResponse> callback) {
        apiService.getTransactionById(id).enqueue(callback);
    }

    public void findTransactionsByAccountNumber(String accountNumber, Callback<List<AccountTransactionResponse>> callback) {
        apiService.findTransactionsByAccountNumber(accountNumber).enqueue(callback);
    }

    public void findTransactionsByAccountId(Long accountId, Callback<List<AccountTransactionResponse>> callback) {
        apiService.findTransactionsByAccountId(accountId).enqueue(callback);
    }

    public void findJournalsByCustomerEmail(String email, Callback<List<JournalResponse>> callback) {
        apiService.findJournalsByCustomerEmail(email).enqueue(callback);
    }

    public void findJournalsByEmailAndSpan(String email, String from, String to, Callback<List<JournalResponse>> callback) {
        apiService.findJournalsByEmailAndSpan(email, from, to).enqueue(callback);
    }

    public void exportStatement(String accountNumber, String from, String to, String format, Callback<ResponseBody> callback) {
        apiService.exportStatement(accountNumber, from, to, format).enqueue(callback);
    }

    public void atmTransaction(ATMTransactionRequest request, Callback<ATMTransactionResponse> callback) {
        apiService.atmTransaction(request).enqueue(callback);
    }

    public void checkAtmBalance(BalanceCheckRequest request, Callback<BigDecimal> callback) {
        apiService.checkAtmBalance(request).enqueue(callback);
    }

    public void getAtmTransactionsByCardNumber(String cardNumber, Callback<List<ATMTransactionResponse>> callback) {
        apiService.getAtmTransactionsByCardNumber(cardNumber).enqueue(callback);
    }
}
