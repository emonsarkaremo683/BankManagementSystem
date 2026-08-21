package com.ensark.ensarkbank.ui.transfers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.ensark.ensarkbank.R;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ensark.ensarkbank.databinding.FragmentListBinding;

public class TransferSuccessFragment extends Fragment {
    private FragmentListBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentListBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("Success");
        binding.recyclerView.setVisibility(View.GONE);
        binding.getRoot().findViewById(R.id.emptyState).setVisibility(View.VISIBLE);
        binding.swipeRefresh.setEnabled(false);
        binding.progress.setVisibility(View.GONE);
        binding.getRoot().findViewById(R.id.errorState).setVisibility(View.GONE);
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
