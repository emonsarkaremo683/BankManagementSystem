package com.ensark.ensarkbank.ui.loan;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemEmiBinding;
import com.ensark.ensarkbank.model.dto.LoanScheduleResponse;
import com.ensark.ensarkbank.model.enums.RepaymentStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmiAdapter extends RecyclerView.Adapter<EmiAdapter.ViewHolder> {

    private final List<LoanScheduleResponse> emis = new ArrayList<>();
    private OnEmiClickListener listener;

    public interface OnEmiClickListener {
        void onPayClick(LoanScheduleResponse emi);
    }

    public void setListener(OnEmiClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEmis(List<LoanScheduleResponse> newEmis) {
        emis.clear();
        if (newEmis != null) {
            emis.addAll(newEmis);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmiBinding binding = ItemEmiBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(emis.get(position));
    }

    @Override
    public int getItemCount() {
        return emis.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemEmiBinding binding;

        ViewHolder(ItemEmiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(LoanScheduleResponse emi) {
            binding.installmentLabel.setText("Installment #" + emi.getInstallmentNumber());
            binding.emiAmountText.setText("৳ " + emi.getEmiAmount().toString());

            if (emi.getDueDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                binding.dueDateText.setText("Due: " + sdf.format(emi.getDueDate()));
            }

            if (emi.getStatus() == RepaymentStatus.PAID) {
                binding.payEmiButton.setVisibility(View.GONE);
                binding.paidStatusText.setVisibility(View.VISIBLE);
            } else {
                binding.payEmiButton.setVisibility(View.VISIBLE);
                binding.paidStatusText.setVisibility(View.GONE);
                binding.payEmiButton.setOnClickListener(v -> {
                    if (listener != null) listener.onPayClick(emi);
                });
            }
        }
    }
}
