package com.ensark.ensarkbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.ensark.ensarkbank.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.adapter.StandingOrderAdapter;
import com.ensark.ensarkbank.databinding.ActivityStandingOrderBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.repository.StandingOrderRepository;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandingOrderActivity extends AppCompatActivity {
    private ActivityStandingOrderBinding binding;
    private StandingOrderAdapter adapter;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStandingOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        adapter = new StandingOrderAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        binding.fab.setOnClickListener(v -> startActivity(new Intent(this, StandingOrderFormActivity.class)));
        binding.swipeRefresh.setOnRefreshListener(this::load);
        load();
    }
    private void load() {
        binding.swipeRefresh.setRefreshing(true);
        String email = "";
        SessionManager sm = new SessionManager(this);
        if (sm.getUser()!=null && sm.getUser().getName()!=null) email = sm.getUser().getName();
        else if (sm.getCustomer()!=null && sm.getCustomer().getEmail()!=null) email = sm.getCustomer().getEmail();
        new AccountRepository(this).findByCustomerEmail(email, new Callback<List<AccountResponse>>() {
            @Override public void onResponse(Call<List<AccountResponse>> call, Response<List<AccountResponse>> res) {
                if (res.isSuccessful() && res.body()!=null && !res.body().isEmpty()) {
                    Long accountId = res.body().get(0).getId();
                    new StandingOrderRepository(StandingOrderActivity.this).findByAccountId(accountId, new Callback<List<StandingOrderResponse>>() {
                        @Override public void onResponse(Call<List<StandingOrderResponse>> c, Response<List<StandingOrderResponse>> r) {
                            binding.swipeRefresh.setRefreshing(false);
                            if (r.isSuccessful() && r.body()!=null) {
                                adapter.update(r.body());
                                binding.getRoot().findViewById(R.id.emptyState).setVisibility(r.body().isEmpty()?View.VISIBLE:View.GONE);
                            }
                        }
                        @Override public void onFailure(Call<List<StandingOrderResponse>> c, Throwable t) { binding.swipeRefresh.setRefreshing(false); }
                    });
                } else binding.swipeRefresh.setRefreshing(false);
            }
            @Override public void onFailure(Call<List<AccountResponse>> call, Throwable t) { binding.swipeRefresh.setRefreshing(false); }
        });
    }
}
