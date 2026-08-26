package com.ensark.ensarkbank.ui.transfer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemBeneficiaryBinding;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder> {

    private final List<BeneficiaryResponse> beneficiaries = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDelete(BeneficiaryResponse beneficiary);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBeneficiaries(List<BeneficiaryResponse> newItems) {
        beneficiaries.clear();
        if (newItems != null) {
            beneficiaries.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBeneficiaryBinding binding = ItemBeneficiaryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(beneficiaries.get(position));
    }

    @Override
    public int getItemCount() {
        return beneficiaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemBeneficiaryBinding binding;

        ViewHolder(ItemBeneficiaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(BeneficiaryResponse item) {
            binding.beneficiaryName.setText(item.getName());
            binding.beneficiaryAccount.setText("Acc: " + item.getAccNumber());
            
            binding.actionMenu.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(item);
            });
        }
    }
}
