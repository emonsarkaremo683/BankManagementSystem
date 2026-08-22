package com.ensark.ensarkbank.ui.auth;

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.ensark.ensarkbank.databinding.ActivityRegisterBinding;
import com.ensark.ensarkbank.ui.base.BaseActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected ActivityRegisterBinding inflateBinding(LayoutInflater inflater) {
        return ActivityRegisterBinding.inflate(inflater);
    }

    @Override
    protected void onInit() {
        binding.registerButton.setOnClickListener(v -> {
            Toast.makeText(this, "Registration logic goes here", Toast.LENGTH_SHORT).show();
        });

        binding.loginLink.setOnClickListener(v -> finish());
    }
}
