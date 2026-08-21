package com.ensark.ensarkbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.AuthApiService;
import com.ensark.ensarkbank.databinding.ActivityOtpVerificationBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.LoginResponse;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {

    private ActivityOtpVerificationBinding binding;
    private String mode = "MFA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mode = getIntent().getStringExtra("mode") != null ? getIntent().getStringExtra("mode") : "MFA";
        binding.btnVerify.setOnClickListener(v -> verify());
    }

    private void verify() {
        String code = binding.etOtp.getText() != null ? binding.etOtp.getText().toString().trim() : "";
        if (code.length() < 6) { binding.etOtp.setError("Enter 6-digit code"); return; }
        if ("MFA".equals(mode)) verifyMfa(code);
        else {
            Intent data = new Intent();
            data.putExtra("otp", code);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void verifyMfa(String code) {
        binding.progress.setVisibility(View.VISIBLE);
        String email = getIntent().getStringExtra("email");
        Map<String,String> body = new HashMap<>();
        body.put("email", email != null ? email : "");
        body.put("totpCode", code);
        AuthApiService api = ApiClient.getClient(this).create(AuthApiService.class);
        api.verifyMfa(body).enqueue(new Callback<LoginResponse<CustomerResponse>>() {
            @Override public void onResponse(Call<LoginResponse<CustomerResponse>> call, Response<LoginResponse<CustomerResponse>> res) {
                binding.progress.setVisibility(View.GONE);
                if (res.isSuccessful() && res.body() != null) {
                    SessionManager sm = new SessionManager(OtpVerificationActivity.this);
                    sm.saveToken(res.body().getToken());
                    sm.saveUser(res.body());
                    if (res.body().getUser()!=null) sm.saveCustomer(res.body().getUser());
                    startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));
                    finishAffinity();
                } else Toast.makeText(OtpVerificationActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<LoginResponse<CustomerResponse>> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(OtpVerificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
