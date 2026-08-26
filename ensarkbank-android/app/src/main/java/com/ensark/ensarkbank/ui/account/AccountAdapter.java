package com.ensark.ensarkbank.ui.account;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemAccountBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private final List<AccountResponse> accounts = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setAccounts(List<AccountResponse> newAccounts) {
        accounts.clear();
        if (newAccounts != null) {
            accounts.addAll(newAccounts);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAccountBinding binding = ItemAccountBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(accounts.get(position));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAccountBinding binding;

        ViewHolder(ItemAccountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(AccountResponse account) {
            binding.accountNumber.setText(account.getAccountNumber());
            binding.accountType.setText(account.getAccountType().name());
            binding.accountStatus.setText(account.getAccountStatus().name());
            binding.balanceValue.setText("৳ " + account.getAvailableBalance().toString());
            binding.branchName.setText(account.getBranchName());
        }
    }
}
