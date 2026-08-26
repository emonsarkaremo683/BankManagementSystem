package com.ensark.ensarkbank.ui.cheque;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemChequeLeafBinding;
import com.ensark.ensarkbank.model.dto.ChequeLeafResponse;

import java.util.ArrayList;
import java.util.List;

public class ChequeLeafAdapter extends RecyclerView.Adapter<ChequeLeafAdapter.ViewHolder> {

    private final List<ChequeLeafResponse> leaves = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setLeaves(List<ChequeLeafResponse> newLeaves) {
        leaves.clear();
        if (newLeaves != null) {
            leaves.addAll(newLeaves);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChequeLeafBinding binding = ItemChequeLeafBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(leaves.get(position));
    }

    @Override
    public int getItemCount() {
        return leaves.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChequeLeafBinding binding;

        ViewHolder(ItemChequeLeafBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(ChequeLeafResponse leaf) {
            binding.leafNumber.setText("Leaf #" + leaf.getLeafNumber());
            binding.leafStatus.setText(leaf.getStatus().name());
            binding.payeeName.setText(leaf.getPayeeName() != null ? leaf.getPayeeName() : "---");
            binding.amountValue.setText(leaf.getAmount() != null ? "৳ " + leaf.getAmount().toString() : "---");
        }
    }
}
