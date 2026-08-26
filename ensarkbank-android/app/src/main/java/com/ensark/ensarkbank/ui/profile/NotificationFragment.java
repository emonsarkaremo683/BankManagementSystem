package com.ensark.ensarkbank.ui.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.databinding.FragmentNotificationsBinding;
import com.ensark.ensarkbank.ui.base.BaseFragment;

public class NotificationFragment extends BaseFragment<FragmentNotificationsBinding> {

    private NotificationViewModel viewModel;
    private NotificationAdapter adapter;

    @Override
    protected FragmentNotificationsBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentNotificationsBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        adapter = new NotificationAdapter();

        setupRecyclerView();
        observeViewModel();

        viewModel.fetchNotifications();
    }

    @Override
    protected void onRefresh() {
        viewModel.fetchNotifications();
    }

    private void setupRecyclerView() {
        binding.notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationsRecyclerView.setAdapter(adapter);

        adapter.setListener(item -> {
            if (!item.isRead()) {
                viewModel.markAsRead(item.getId());
            }
        });
    }

    private void observeViewModel() {
        viewModel.notifications.observe(getViewLifecycleOwner(), notifications -> {
            setRefreshing(false);
            if (notifications != null) {
                adapter.setNotifications(notifications);
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            // Handle progress bar if needed
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }
}
