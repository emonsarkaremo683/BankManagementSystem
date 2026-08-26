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

        setLoading(true);
        LoginRequest request = new LoginRequest(email, password);
        authRepository.login(request, new Callback<LoginResponse<CustomerResponse>>() {
            @Override
            public void onResponse(Call<LoginResponse<CustomerResponse>> call, Response<LoginResponse<CustomerResponse>> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    sessionManager.saveToken(response.body().getToken());
                    sessionManager.saveCustomer(response.body().getUser()); // Save customer data
                    startActivity(new Intent(LoginActivity.this, NavHostActivity.class));
                    finish();
                } else {
                    String error = "Login failed";
                    if (response.errorBody() != null) {
                        try {
                            error += ": " + response.errorBody().string();
                        } catch (Exception ignored) {}
                    }
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse<CustomerResponse>> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        binding.loginButton.setEnabled(!isLoading);
        binding.loginButton.setText(isLoading ? "" : "Login");
        binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
    }
}
