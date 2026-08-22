package com.ensark.ensarkbank.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentDashboardBinding;
import com.ensark.ensarkbank.ui.base.BaseFragment;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {

    private DashboardViewModel viewModel;
    private TransactionAdapter adapter;

    @Override
    protected FragmentDashboardBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDashboardBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        adapter = new TransactionAdapter();

        setupRecyclerView();
        observeViewModel();

        binding.actionGrid.getChildAt(0).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_transferFragment);
        });

        binding.actionGrid.getChildAt(1).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_cardFragment);
        });

        viewModel.fetchDashboard();
    }

    private void setupRecyclerView() {
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecyclerView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        viewModel.dashboardData.observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                binding.balanceText.setText("$ " + data.getBalance().toString());
                adapter.setTransactions(data.getRecentTransactions());
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            // Show/hide loading indicator if added
        });
    }
}
