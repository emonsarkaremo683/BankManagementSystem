package com.ensark.ensarkbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.api.AuthApiService;
import com.ensark.ensarkbank.databinding.ActivityLoginBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.LoginRequest;
import com.ensark.ensarkbank.model.dto.LoginResponse;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new SessionManager(this);
        binding.tvForgot.setOnClickListener(v -> startActivity(new Intent(this, ForgetPasswordActivity.class)));
        binding.tvRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        binding.btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString() : "";
        boolean valid = true;
        if (email.isEmpty()) { binding.tilEmail.setError("Email required"); valid = false; } else binding.tilEmail.setError(null);
        if (password.isEmpty()) { binding.tilPassword.setError("Password required"); valid = false; } else binding.tilPassword.setError(null);
        if (!valid) return;
        setLoading(true);
        AuthApiService api = ApiClient.getClient(this).create(AuthApiService.class);
        api.login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse<CustomerResponse>>() {
            @Override public void onResponse(Call<LoginResponse<CustomerResponse>> call, Response<LoginResponse<CustomerResponse>> res) {
                setLoading(false);
                if (res.isSuccessful() && res.body() != null) {
                    LoginResponse<CustomerResponse> body = res.body();
                    if (body.isMfaRequired()) {
                        Intent i = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                        i.putExtra("mode", "MFA");
                        i.putExtra("email", email);
                        i.putExtra("password", password);
                        startActivity(i);
                        return;
                    }
                    session.saveToken(body.getToken());
                    session.saveUser(body);
                    if (body.getUser() != null) session.saveCustomer(body.getUser());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed. Check credentials.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<LoginResponse<CustomerResponse>> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.btnLogin.setEnabled(!loading);
    }
}
