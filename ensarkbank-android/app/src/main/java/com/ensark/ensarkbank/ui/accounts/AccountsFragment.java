package com.ensark.ensarkbank.ui.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.ensark.ensarkbank.R;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.adapter.AccountAdapter;
import com.ensark.ensarkbank.databinding.FragmentListBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsFragment extends Fragment {
    private FragmentListBinding binding;
    private AccountAdapter adapter;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentListBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("My Accounts");
        adapter = new AccountAdapter(new ArrayList<>(), item -> {});
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.fab.setVisibility(View.VISIBLE);
        binding.fab.setText("Open Account");
        binding.fab.setOnClickListener(v -> startActivity(new android.content.Intent(requireContext(), com.ensark.ensarkbank.activity.AccountOpeningActivity.class)));
        binding.swipeRefresh.setOnRefreshListener(this::load);
        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(com.ensark.ensarkbank.R.color.brand_primary, null));
        binding.getRoot().findViewById(R.id.btnRetry).setOnClickListener(v -> load());
        load();
    }

    private void load() {
        binding.swipeRefresh.setRefreshing(true);
        binding.progress.setVisibility(View.VISIBLE);
        String email = "";
        SessionManager sm = new SessionManager(requireContext());
        if (sm.getUser()!=null && sm.getUser().getName()!=null) email = sm.getUser().getName();
        else if (sm.getCustomer()!=null && sm.getCustomer().getEmail()!=null) email = sm.getCustomer().getEmail();
        new AccountRepository(requireContext()).findByCustomerEmail(email, new Callback<List<AccountResponse>>() {
            @Override public void onResponse(Call<List<AccountResponse>> call, Response<List<AccountResponse>> res) {
                binding.swipeRefresh.setRefreshing(false);
                binding.progress.setVisibility(View.GONE);
                if (res.isSuccessful() && res.body()!=null) {
                    adapter.update(res.body());
                    binding.getRoot().findViewById(R.id.emptyState).setVisibility(res.body().isEmpty()?View.VISIBLE:View.GONE);
                    binding.getRoot().findViewById(R.id.errorState).setVisibility(View.GONE);
                } else {
                    binding.getRoot().findViewById(R.id.errorState).setVisibility(View.VISIBLE);
                }
            }
            @Override public void onFailure(Call<List<AccountResponse>> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                binding.progress.setVisibility(View.GONE);
                binding.getRoot().findViewById(R.id.errorState).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
