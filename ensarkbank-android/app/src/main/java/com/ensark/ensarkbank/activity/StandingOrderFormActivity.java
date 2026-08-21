package com.ensark.ensarkbank.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.databinding.ActivityFormSimpleBinding;
import com.ensark.ensarkbank.model.dto.StandingOrderRequest;
import com.ensark.ensarkbank.repository.StandingOrderRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandingOrderFormActivity extends AppCompatActivity {
    private ActivityFormSimpleBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormSimpleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("New Standing Order");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        String[] freq = new String[]{"DAILY","WEEKLY","MONTHLY","YEARLY"};
        binding.actDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, freq));
        binding.btnSubmit.setOnClickListener(v -> {
            StandingOrderRequest req = new StandingOrderRequest();
            new StandingOrderRepository(this).create(req, new Callback<com.ensark.ensarkbank.model.dto.StandingOrderResponse>() {
                @Override public void onResponse(Call<com.ensark.ensarkbank.model.dto.StandingOrderResponse> call, Response<com.ensark.ensarkbank.model.dto.StandingOrderResponse> res) {
                    if (res.isSuccessful()) { Toast.makeText(StandingOrderFormActivity.this,"Created",Toast.LENGTH_SHORT).show(); finish(); }
                    else Toast.makeText(StandingOrderFormActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
                @Override public void onFailure(Call<com.ensark.ensarkbank.model.dto.StandingOrderResponse> call, Throwable t) { Toast.makeText(StandingOrderFormActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show(); }
            });
        });
    }
}
