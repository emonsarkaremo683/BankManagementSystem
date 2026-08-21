package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.GeneralApiService;
import com.ensark.ensarkbank.databinding.ActivityCurrencyConverterBinding;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyConverterActivity extends AppCompatActivity {
    private ActivityCurrencyConverterBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        String[] currencies = new String[]{"BDT","USD","EUR","GBP","JPY"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencies);
        binding.actFrom.setAdapter(ad);
        binding.actTo.setAdapter(ad);
        binding.actFrom.setText("BDT", false);
        binding.actTo.setText("USD", false);
        binding.btnSwap.setOnClickListener(v -> {
            String f = binding.actFrom.getText().toString();
            String t = binding.actTo.getText().toString();
            binding.actFrom.setText(t, false);
            binding.actTo.setText(f, false);
        });
        binding.btnConvert.setOnClickListener(v -> convert());
    }
    private void convert() {
        String amtStr = binding.etAmount.getText()!=null?binding.etAmount.getText().toString():"0";
        String from = binding.actFrom.getText().toString();
        String to = binding.actTo.getText().toString();
        if (amtStr.isEmpty()) { Toast.makeText(this,"Enter amount",Toast.LENGTH_SHORT).show(); return; }
        java.math.BigDecimal amt;
        try { amt = new java.math.BigDecimal(amtStr); } catch (Exception e) { amt = java.math.BigDecimal.ZERO; }
        GeneralApiService api = ApiClient.getClient(this).create(GeneralApiService.class);
        api.convertCurrency(from, to, amt).enqueue(new Callback<Map<String,Object>>() {
            @Override public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> res) {
                if (res.isSuccessful() && res.body()!=null) {
                    Object result = res.body().get("result");
                    if (result==null) result = res.body().get("convertedAmount");
                    if (result==null) result = res.body().toString();
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.tvResult.setText(String.valueOf(result));
                } else Toast.makeText(CurrencyConverterActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<Map<String,Object>> call, Throwable t) { Toast.makeText(CurrencyConverterActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show(); }
        });
    }
}
