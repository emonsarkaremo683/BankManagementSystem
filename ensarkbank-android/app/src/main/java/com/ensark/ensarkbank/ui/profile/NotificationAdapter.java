package com.ensark.ensarkbank.ui.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemNotificationBinding;
import com.ensark.ensarkbank.model.dto.NotificationResponse;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<NotificationResponse> notifications = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NotificationResponse item);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setNotifications(List<NotificationResponse> newNotifications) {
        notifications.clear();
        if (newNotifications != null) {
            notifications.addAll(newNotifications);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationResponse item = notifications.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding binding;

        ViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(NotificationResponse item) {
            binding.notificationTitle.setText(item.getTitle());
            binding.notificationMessage.setText(item.getMessage());
            binding.notificationTime.setText(item.getCreatedAt());
            
            // Highlight unread
            if (!item.isRead()) {
                binding.getRoot().setAlpha(1.0f);
                binding.notificationTitle.setTypeface(null, android.graphics.Typeface.BOLD);
            } else {
                binding.getRoot().setAlpha(0.6f);
                binding.notificationTitle.setTypeface(null, android.graphics.Typeface.NORMAL);
            }
        }
    }
}
