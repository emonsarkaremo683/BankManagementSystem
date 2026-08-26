package com.ensark.ensarkbank.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ItemTransactionBinding;
import com.ensark.ensarkbank.model.dto.JournalResponse;
import com.ensark.ensarkbank.model.enums.EntryType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<JournalResponse> transactions = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

    @SuppressLint("NotifyDataSetChanged")
    public void setTransactions(List<JournalResponse> newTransactions) {
        transactions.clear();
        if (newTransactions != null) {
            transactions.addAll(newTransactions);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionBinding binding = ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(transactions.get(position));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionBinding binding;

        ViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(JournalResponse transaction) {
            binding.transactionTitle.setText(transaction.getParticulars());
            binding.transactionDate.setText(dateFormat.format(transaction.getDate()));
            
            String prefix = transaction.getEntryType() == EntryType.DEBIT ? "-" : "+";
            int color = transaction.getEntryType() == EntryType.DEBIT ? R.color.neon_pink : R.color.mint_green;
            
            binding.transactionAmount.setText(prefix + "৳ " + transaction.getAmount().toString());
            binding.transactionAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), color));
            binding.typeIndicator.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), color));
        }
    }
}
