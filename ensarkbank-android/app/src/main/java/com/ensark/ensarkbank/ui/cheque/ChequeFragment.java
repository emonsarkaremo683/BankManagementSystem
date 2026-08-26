package com.ensark.ensarkbank.ui.cheque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentChequeBinding;
import com.ensark.ensarkbank.ui.base.BaseFragment;
import com.google.gson.Gson;

public class ChequeFragment extends BaseFragment<FragmentChequeBinding> {

    private ChequeViewModel viewModel;
    private ChequeAdapter adapter;

    @Override
    protected FragmentChequeBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChequeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ChequeViewModel.class);
        adapter = new ChequeAdapter();

        setupRecyclerView();
        observeViewModel();

        if (sessionManager.getCustomer() != null) {
            viewModel.fetchChequeBooks(sessionManager.getCustomer().getEmail());
        }

        binding.requestChequeButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Cheque book request initiated", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        binding.chequeBooksRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.chequeBooksRecyclerView.setAdapter(adapter);

        adapter.setListener(book -> {
            Bundle args = new Bundle();
            args.putString("chequeBook", new Gson().toJson(book));
            Navigation.findNavController(requireView()).navigate(R.id.action_chequeFragment_to_chequeLeafFragment, args);
        });
    }

    private void observeViewModel() {
        viewModel.chequeBooks.observe(getViewLifecycleOwner(), books -> {
            if (books != null) {
                adapter.setChequeBooks(books);
            }
        });
    }
}
