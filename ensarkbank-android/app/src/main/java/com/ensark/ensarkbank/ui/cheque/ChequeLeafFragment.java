package com.ensark.ensarkbank.ui.cheque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.databinding.FragmentChequeLeavesBinding;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;
import com.google.gson.Gson;

public class ChequeLeafFragment extends BaseFragment<FragmentChequeLeavesBinding> {

    private ChequeBookResponse chequeBook;
    private ChequeLeafAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String json = getArguments().getString("chequeBook");
            chequeBook = new Gson().fromJson(json, ChequeBookResponse.class);
        }
    }

    @Override
    protected FragmentChequeLeavesBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChequeLeavesBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        adapter = new ChequeLeafAdapter();
        setupRecyclerView();

        if (chequeBook != null) {
            binding.bookInfo.setText("Book #" + chequeBook.getBookSerialNumber() + " | " + chequeBook.getAccountNumber());
            if (chequeBook.getLeaves() != null) {
                adapter.setLeaves(chequeBook.getLeaves());
            }
        }
    }

    private void setupRecyclerView() {
        binding.leavesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.leavesRecyclerView.setAdapter(adapter);
    }
}
