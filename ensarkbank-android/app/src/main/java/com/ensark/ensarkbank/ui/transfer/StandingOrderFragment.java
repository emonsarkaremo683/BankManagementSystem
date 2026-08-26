package com.ensark.ensarkbank.ui.transfer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentStandingOrdersBinding;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;
import com.ensark.ensarkbank.model.enums.StandingOrderStatus;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.math.BigDecimal;
import java.util.List;

public class StandingOrderFragment extends BaseFragment<FragmentStandingOrdersBinding> {

    private StandingOrderViewModel viewModel;
    private StandingOrderAdapter adapter;

    @Override
    protected FragmentStandingOrdersBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStandingOrdersBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(StandingOrderViewModel.class);
        adapter = new StandingOrderAdapter();

        setupRecyclerView();
        observeViewModel();

        fetchData();

        binding.setNewOrderButton.setOnClickListener(v -> 
            Navigation.findNavController(v).navigate(R.id.action_standingOrderFragment_to_createStandingOrderFragment));
    }

    private void fetchData() {
        // In production, we would select an account first. For now, fetch for customer's first account if available
        viewModel.fetchStandingOrders(1L); // Sample ID
    }

    @Override
    protected void onRefresh() {
        fetchData();
    }

    private void setupRecyclerView() {
        binding.soRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.soRecyclerView.setAdapter(adapter);

        adapter.setListener(new StandingOrderAdapter.OnActionClickListener() {
            @Override
            public void onPauseResume(StandingOrderResponse order) {
                if (order.getStatus() == StandingOrderStatus.ACTIVE) {
                    viewModel.pauseOrder(order.getId(), 1L);
                } else {
                    viewModel.resumeOrder(order.getId(), 1L);
                }
            }

            @Override
            public void onDelete(StandingOrderResponse order) {
                Toast.makeText(requireContext(), "Delete requested", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        viewModel.standingOrders.observe(getViewLifecycleOwner(), orders -> {
            setRefreshing(false);
            if (orders != null) {
                adapter.setOrders(orders);
                updateSummary(orders);
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateSummary(List<StandingOrderResponse> orders) {
        BigDecimal nextAmount = BigDecimal.ZERO;
        String nextDateStr = "No upcoming payments";

        for (StandingOrderResponse order : orders) {
            if (order.getStatus() == StandingOrderStatus.ACTIVE) {
                nextAmount = order.getAmount();
                if (order.getNextExecutionDate() != null) {
                    nextDateStr = "Next execution: " + order.getNextExecutionDate().toString();
                }
                break;
            }
        }
        binding.nextAmount.setText("৳ " + nextAmount.toString());
        binding.nextDate.setText(nextDateStr);
    }
}
