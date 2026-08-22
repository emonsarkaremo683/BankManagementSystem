package com.ensark.ensarkbank.ui.auth;

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.ensark.ensarkbank.databinding.ActivityLoginBinding;
import com.ensark.ensarkbank.model.dto.LoginRequest;
import com.ensark.ensarkbank.model.dto.LoginResponse;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.repository.AuthRepository;
import com.ensark.ensarkbank.ui.NavHostActivity;
import com.ensark.ensarkbank.ui.base.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private AuthRepository authRepository;

    @Override
    protected ActivityLoginBinding inflateBinding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void onInit() {
        authRepository = new AuthRepository(this);

        binding.loginButton.setOnClickListener(v -> handleLogin());
        binding.registerLink.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void handleLogin() {
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest request = new LoginRequest(email, password);
        authRepository.login(request, new Callback<LoginResponse<CustomerResponse>>() {
            @Override
            public void onResponse(Call<LoginResponse<CustomerResponse>> call, Response<LoginResponse<CustomerResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body().getToken());
                    startActivity(new Intent(LoginActivity.this, NavHostActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse<CustomerResponse>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
