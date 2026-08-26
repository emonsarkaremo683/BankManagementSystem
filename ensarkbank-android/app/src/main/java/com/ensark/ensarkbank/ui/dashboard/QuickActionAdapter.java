package com.ensark.ensarkbank.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ItemQuickActionBinding;

import java.util.ArrayList;
import java.util.List;

public class QuickActionAdapter extends RecyclerView.Adapter<QuickActionAdapter.ViewHolder> {

    private final List<QuickAction> actions = new ArrayList<>();
    private final OnActionClickListener listener;

    public interface OnActionClickListener {
        void onActionClick(QuickAction action);
    }

    public QuickActionAdapter(OnActionClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setActions(List<QuickAction> newActions) {
        actions.clear();
        if (newActions != null) {
            actions.addAll(newActions);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuickActionBinding binding = ItemQuickActionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(actions.get(position));
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemQuickActionBinding binding;

        ViewHolder(ItemQuickActionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(QuickAction action) {
            binding.actionTitle.setText(action.getTitle());
            binding.actionIcon.setImageResource(action.getIcon());
            binding.actionIcon.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.getContext(), action.getTintColor())));

            if (action.getCount() > 0) {
                binding.actionCount.setText(String.valueOf(action.getCount()));
                binding.actionCount.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), action.getTintColor())));
                binding.actionCount.setVisibility(View.VISIBLE);
            } else {
                binding.actionCount.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onActionClick(action);
                }
            });
        }
    }
}
