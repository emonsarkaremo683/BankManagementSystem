package com.ensark.ensarkbank.ui.account;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentAccountListBinding;
import com.ensark.ensarkbank.ui.base.BaseFragment;
import com.ensark.ensarkbank.ui.dashboard.DashboardViewModel;

public class AccountListFragment extends BaseFragment<FragmentAccountListBinding> {

    private DashboardViewModel viewModel;
    private AccountAdapter adapter;

    @Override
    protected FragmentAccountListBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAccountListBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        // Reuse DashboardViewModel from Activity scope so it doesn't refetch
        viewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(DashboardViewModel.class);
        adapter = new AccountAdapter();

        setupRecyclerView();
        observeViewModel();

        binding.openAccountButton.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_accountListFragment_to_createAccountFragment));

        viewModel.fetchDashboard();
    }

    @Override
    protected void onRefresh() {
        viewModel.fetchDashboard();
    }

    private void setupRecyclerView() {
        binding.accountsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.accountsRecyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.dashboardData.observe(getViewLifecycleOwner(), data -> {
            setRefreshing(false);
            if (data != null && data.getAccounts() != null) {
                adapter.setAccounts(data.getAccounts());
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
        });
    }
}
