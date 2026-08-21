package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.AuthApiService;
import com.ensark.ensarkbank.databinding.ActivityResetPasswordBinding;
import com.ensark.ensarkbank.model.dto.ResetPasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private ActivityResetPasswordBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.btnReset.setOnClickListener(v -> reset());
    }
    private void reset() {
        String p = binding.etPassword.getText()!=null?binding.etPassword.getText().toString():"";
        String c = binding.etConfirm.getText()!=null?binding.etConfirm.getText().toString():"";
        if (p.isEmpty()||!p.equals(c)) { Toast.makeText(this,"Passwords must match",Toast.LENGTH_SHORT).show(); return; }
        String token = getIntent().getStringExtra("token");
        if (token==null) token = getIntent().getData()!=null?getIntent().getData().getQueryParameter("token"):"";
        ResetPasswordRequest req = new ResetPasswordRequest();
        req.setToken(token!=null?token:"");
        req.setNewPassword(p);
        ApiClient.getClient(this).create(AuthApiService.class).resetPassword(req).enqueue(new Callback<String>() {
            @Override public void onResponse(Call<String> call, Response<String> r){ Toast.makeText(ResetPasswordActivity.this, r.isSuccessful()?"Password reset":"Failed",Toast.LENGTH_SHORT).show(); if(r.isSuccessful()) finish(); }
            @Override public void onFailure(Call<String> call, Throwable t){ Toast.makeText(ResetPasswordActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show(); }
        });
    }
}
