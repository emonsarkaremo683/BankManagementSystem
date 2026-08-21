package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.AuthApiService;
import com.ensark.ensarkbank.databinding.ActivityForgetPasswordBinding;
import com.ensark.ensarkbank.model.dto.ForgetPasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    private ActivityForgetPasswordBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.btnSend.setOnClickListener(v -> send());
    }
    private void send() {
        String email = binding.etEmail.getText()!=null?binding.etEmail.getText().toString().trim():"";
        if (email.isEmpty()) { binding.tilEmail.setError("Required"); return; }
        binding.tilEmail.setError(null);
        ForgetPasswordRequest req = new ForgetPasswordRequest();
        req.setEmail(email);
        ApiClient.getClient(this).create(AuthApiService.class).forgotPassword(req).enqueue(new Callback<String>() {
            @Override public void onResponse(Call<String> c, Response<String> r) { Toast.makeText(ForgetPasswordActivity.this, r.isSuccessful()?"Reset link sent":"Failed", Toast.LENGTH_SHORT).show(); if(r.isSuccessful()) finish(); }
            @Override public void onFailure(Call<String> c, Throwable t) { Toast.makeText(ForgetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show(); }
        });
    }
}
