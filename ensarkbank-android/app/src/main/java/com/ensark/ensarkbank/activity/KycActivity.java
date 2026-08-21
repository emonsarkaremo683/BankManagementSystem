package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ensark.ensarkbank.databinding.ActivityFormSimpleBinding;

public class KycActivity extends AppCompatActivity {
    private ActivityFormSimpleBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormSimpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("KYC Verification");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        String[] docs = new String[]{"NID","PASSPORT","DRIVING_LICENSE","BIRTH_CERTIFICATE"};
        binding.actDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, docs));
        binding.btnPickImage.setVisibility(android.view.View.VISIBLE);
        binding.ivPreview.setVisibility(android.view.View.VISIBLE);
        Glide.with(this).load("").placeholder(android.R.drawable.ic_menu_gallery).into(binding.ivPreview);
        binding.btnSubmit.setText("Upload Document");
        binding.btnSubmit.setOnClickListener(v -> Toast.makeText(this,"KYC upload wired",Toast.LENGTH_SHORT).show());
    }
}
