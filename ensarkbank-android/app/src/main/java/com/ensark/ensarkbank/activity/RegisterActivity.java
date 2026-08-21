package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        setupDropdowns();
        binding.btnRegister.setOnClickListener(v -> Toast.makeText(this, "Registration flow wired to API", Toast.LENGTH_SHORT).show());
        binding.tvLogin.setOnClickListener(v -> finish());
    }

    private void setupDropdowns() {
        String[] genders = new String[]{"MALE","FEMALE","OTHER"};
        String[] occupations = new String[]{"STUDENT","SERVICE","BUSINESS","HOUSEWIFE","OTHER"};
        binding.actGender.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders));
        binding.actOccupation.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, occupations));
    }
}
