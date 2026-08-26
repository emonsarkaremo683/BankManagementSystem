package com.ensark.ensarkbank.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.databinding.FragmentDashboardBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {

    private DashboardViewModel viewModel;
    private TransactionAdapter adapter;
    private QuickActionAdapter actionAdapter;
    private List<QuickAction> quickActions;

    @Override
    protected FragmentDashboardBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDashboardBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(DashboardViewModel.class);
        adapter = new TransactionAdapter();
        actionAdapter = new QuickActionAdapter(action -> {
            Navigation.findNavController(requireView()).navigate(action.getDestinationId());
        });

        setupRecyclerView();
        setupActionRecyclerView();
        observeViewModel();

        binding.profileButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_dashboardFragment_to_profileFragment);
        });

        loadProfileImage();

        viewModel.fetchDashboard();
    }

    @Override
    protected void onRefresh() {
        viewModel.fetchDashboard();
    }

    private void loadProfileImage() {
        if (sessionManager.getCustomer() != null) {
            CustomerResponse customer = sessionManager.getCustomer();
            if (customer.getProfile() != null && !customer.getProfile().isEmpty()) {
                GlideUrl glideUrl = new GlideUrl(ApiClient.IMAGE_URL + customer.getProfile(), new LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                        .build());
                Glide.with(this)
                        .load(glideUrl)
                        .circleCrop()
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .into(binding.profileButton);
            }
        }
    }

    private void setupActionRecyclerView() {
        quickActions = new ArrayList<>();
        quickActions.add(new QuickAction("send", "Send", R.drawable.ic_send, R.color.mint_green, R.id.action_dashboardFragment_to_transferFragment));
        quickActions.add(new QuickAction("cards", "Cards", R.drawable.ic_pay, R.color.electric_cyan, R.id.action_dashboardFragment_to_cardFragment));
        quickActions.add(new QuickAction("loans", "Loans", R.drawable.ic_loans, R.color.neon_pink, R.id.action_dashboardFragment_to_loanFragment));
        quickActions.add(new QuickAction("beneficiaries", "Beneficiaries", R.drawable.ic_contacts, R.color.mint_green, R.id.action_dashboardFragment_to_beneficiaryFragment));
        quickActions.add(new QuickAction("accounts", "Accounts", R.drawable.ic_accounts, R.color.electric_cyan, R.id.action_dashboardFragment_to_accountListFragment));
        quickActions.add(new QuickAction("standing_orders", "Standing Orders", R.drawable.ic_automate, R.color.mint_green, R.id.action_dashboardFragment_to_standingOrderFragment));
        quickActions.add(new QuickAction("cheque", "Cheque", R.drawable.ic_cheque, R.color.white, R.id.action_dashboardFragment_to_chequeFragment));
        quickActions.add(new QuickAction("converter", "Converter", R.drawable.ic_converter, R.color.white, R.id.action_dashboardFragment_to_converterFragment));

        binding.actionRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        binding.actionRecyclerView.setAdapter(actionAdapter);
        actionAdapter.setActions(quickActions);
    }

    private void setupRecyclerView() {
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecyclerView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        viewModel.dashboardData.observe(getViewLifecycleOwner(), data -> {
            setRefreshing(false);
            if (data != null) {
                if (sessionManager.getCustomer() != null) {
                    binding.greetingText.setText("Hello, " + sessionManager.getCustomer().getName() + "!");
                }

                if (data.getBalance() != null) {
                    binding.balanceText.setText("৳ " + data.getBalance().toString());
                }

                // Update Counts from Response
                updateActionCounts(data);

                if (data.getRecentTransactions() != null) {
                    adapter.setTransactions(data.getRecentTransactions());
                }
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            // Show/hide loading indicator if added
        });
    }

    private void updateActionCounts(com.ensark.ensarkbank.model.dto.CustomerDashboardResponse data) {
        for (QuickAction action : quickActions) {
            switch (action.getId()) {
                case "cards":
                    action.setCount(data.getTotalCard() != null ? data.getTotalCard().intValue() : 0);
                    break;
                case "loans":
                    action.setCount(data.getTotalLoan() != null ? data.getTotalLoan().intValue() : 0);
                    break;
                case "beneficiaries":
                    action.setCount(data.getTotalBeneficiary() != null ? data.getTotalBeneficiary().intValue() : 0);
                    break;
                case "accounts":
                    action.setCount(data.getTotalAccount() != null ? data.getTotalAccount().intValue() : 0);
                    break;
            }
        }
        actionAdapter.notifyDataSetChanged();
    }

}
