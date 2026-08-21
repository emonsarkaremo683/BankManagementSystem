package com.ensark.ensarkbank.ui.loans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.ensark.ensarkbank.R;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ensark.ensarkbank.databinding.FragmentListBinding;

public class LoanDetailsFragment extends Fragment {
    private FragmentListBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentListBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("Loan Details");
        binding.recyclerView.setVisibility(View.GONE);
        binding.fab.setVisibility(View.GONE);
        binding.swipeRefresh.setEnabled(false);
        binding.progress.setVisibility(View.GONE);
        String id = getArguments()!=null?String.valueOf(getArguments().getLong("id")):"";
        binding.getRoot().findViewById(R.id.emptyState).setVisibility(View.VISIBLE);
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
