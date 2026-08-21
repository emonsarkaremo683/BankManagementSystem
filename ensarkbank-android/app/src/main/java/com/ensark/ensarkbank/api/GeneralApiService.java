package com.ensark.ensarkbank.api;

import com.ensark.ensarkbank.model.dto.BranchResponse;
import com.ensark.ensarkbank.model.dto.CurrencyResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeneralApiService {

    @GET("api/branch/")
    Call<List<BranchResponse>> getAllBranches();

    @GET("api/branch/{id}")
    Call<BranchResponse> getBranchById(@Path("id") Long id);

    @GET("api/branch/police-station/{policeStationId}")
    Call<List<BranchResponse>> findBranchesByPoliceStation(@Path("policeStationId") Long policeStationId);

    @GET("api/currency/convert")
    Call<Map<String, Object>> convertCurrency(
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") java.math.BigDecimal amount
    );

    @GET("api/currency/")
    Call<List<CurrencyResponse>> getAllCurrencies(@Query("base") String base);

    @GET("api/credit-accounts/{id}/balance")
    Call<Map<String, Object>> getCreditAccountBalance(@Path("id") Long id);
}
