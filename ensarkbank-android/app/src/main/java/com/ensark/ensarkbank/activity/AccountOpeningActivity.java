package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ensark.ensarkbank.databinding.ActivityFormSimpleBinding;

public class AccountOpeningActivity extends AppCompatActivity {
    private ActivityFormSimpleBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormSimpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Open Account");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        String[] types = new String[]{"SAVINGS","CURRENT","STUDENT","SALARY"};
        binding.actDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types));
        binding.ivPreview.setVisibility(android.view.View.VISIBLE);
        binding.btnPickImage.setVisibility(android.view.View.VISIBLE);
        Glide.with(this).load("").placeholder(android.R.drawable.ic_menu_gallery).into(binding.ivPreview);
        binding.btnSubmit.setOnClickListener(v -> Toast.makeText(this,"Account create API wired",Toast.LENGTH_SHORT).show());
    }
}
