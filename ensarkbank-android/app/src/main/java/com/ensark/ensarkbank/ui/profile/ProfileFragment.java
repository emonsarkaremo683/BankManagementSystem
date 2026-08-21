package com.ensark.ensarkbank.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.ensark.ensarkbank.R;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ensark.ensarkbank.databinding.FragmentListBinding;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.repository.CustomerRepository;
import com.ensark.ensarkbank.session.SessionManager;

public class ProfileFragment extends Fragment {
    private FragmentListBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentListBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("My Profile");
        binding.recyclerView.setVisibility(View.GONE);
        binding.fab.setVisibility(View.GONE);
        binding.swipeRefresh.setEnabled(false);
        CustomerResponse customer = new SessionManager(requireContext()).getCustomer();
        if (customer != null) {
            binding.getRoot().findViewById(R.id.emptyState).setVisibility(View.VISIBLE);
        }
        binding.progress.setVisibility(View.GONE);
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
