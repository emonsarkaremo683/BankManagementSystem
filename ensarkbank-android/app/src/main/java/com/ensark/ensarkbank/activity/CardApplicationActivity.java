package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.databinding.ActivityFormSimpleBinding;

public class CardApplicationActivity extends AppCompatActivity {
    private ActivityFormSimpleBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormSimpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Apply for Card");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        String[] networks = new String[]{"VISA","MASTERCARD"};
        binding.actDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, networks));
        binding.btnSubmit.setOnClickListener(v -> Toast.makeText(this,"Card apply wired",Toast.LENGTH_SHORT).show());
    }
}
