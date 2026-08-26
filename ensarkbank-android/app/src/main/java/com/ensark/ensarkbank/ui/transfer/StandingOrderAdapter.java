package com.ensark.ensarkbank.ui.transfer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ItemStandingOrderBinding;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;
import com.ensark.ensarkbank.model.enums.StandingOrderStatus;

import java.util.ArrayList;
import java.util.List;

public class StandingOrderAdapter extends RecyclerView.Adapter<StandingOrderAdapter.ViewHolder> {

    private final List<StandingOrderResponse> orders = new ArrayList<>();
    private OnActionClickListener listener;

    public interface OnActionClickListener {
        void onPauseResume(StandingOrderResponse order);
        void onDelete(StandingOrderResponse order);
    }

    public void setListener(OnActionClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setOrders(List<StandingOrderResponse> newOrders) {
        orders.clear();
        if (newOrders != null) {
            orders.addAll(newOrders);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStandingOrderBinding binding = ItemStandingOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStandingOrderBinding binding;

        ViewHolder(ItemStandingOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(StandingOrderResponse order) {
            binding.targetName.setText(order.getTargetAccountName());
            binding.amountValue.setText("৳ " + order.getAmount().toString());
            binding.frequencyValue.setText(order.getFrequency().name());
            binding.statusBadge.setText(order.getStatus().name());

            int statusColor = order.getStatus() == StandingOrderStatus.ACTIVE ? R.color.mint_green : R.color.text_secondary;
            binding.statusBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), statusColor));

            binding.pauseButton.setOnClickListener(v -> {
                if (listener != null) listener.onPauseResume(order);
            });

            binding.deleteButton.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(order);
            });
        }
    }
}
