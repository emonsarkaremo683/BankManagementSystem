package com.ensark.ensarkbank.api;

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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransactionApiService {

    @POST("api/account-transaction/online/initiate")
    Call<OtpInitiateResponse> initiateOnlineTransaction(@Body AccountTransactionRequest atr);

    @POST("api/account-transaction/online/verify")
    Call<AccountTransactionResponse> verifyOnlineTransaction(@Body OtpVerifyRequest req);

    @GET("api/account-transaction/{id}")
    Call<AccountTransactionResponse> getTransactionById(@Path("id") Long id);

    @GET("api/account-transaction/accountNumber/{accountNumber}")
    Call<List<AccountTransactionResponse>> findTransactionsByAccountNumber(@Path("accountNumber") String accountNumber);

    @GET("api/account-transaction/account/{accountId}")
    Call<List<AccountTransactionResponse>> findTransactionsByAccountId(@Path("accountId") Long accountId);

    @GET("api/journal/customer/{email}")
    Call<List<JournalResponse>> findJournalsByCustomerEmail(@Path("email") String email);

    @GET("api/journal/customer/{email}/span")
    Call<List<JournalResponse>> findJournalsByEmailAndSpan(
            @Path("email") String email,
            @Query("from") String from,
            @Query("to") String to
    );

    @GET("api/journal/account/{accountNumber}/export")
    Call<ResponseBody> exportStatement(
            @Path("accountNumber") String accountNumber,
            @Query("from") String from,
            @Query("to") String to,
            @Query("format") String format
    );

    @POST("api/atm-transactions")
    Call<ATMTransactionResponse> atmTransaction(@Body ATMTransactionRequest request);

    @POST("api/atm-transactions/balance")
    Call<BigDecimal> checkAtmBalance(@Body BalanceCheckRequest request);

    @GET("api/atm-transactions/card/{cardNumber}")
    Call<List<ATMTransactionResponse>> getAtmTransactionsByCardNumber(@Path("cardNumber") String cardNumber);
}
