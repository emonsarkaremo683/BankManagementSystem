package com.ensark.ensarkbank.ui.cheque;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemChequeBookBinding;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;

import java.util.ArrayList;
import java.util.List;

public class ChequeAdapter extends RecyclerView.Adapter<ChequeAdapter.ViewHolder> {

    private final List<ChequeBookResponse> chequeBooks = new ArrayList<>();
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(ChequeBookResponse book);
    }

    public void setListener(OnBookClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setChequeBooks(List<ChequeBookResponse> newBooks) {
        chequeBooks.clear();
        if (newBooks != null) {
            chequeBooks.addAll(newBooks);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChequeBookBinding binding = ItemChequeBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(chequeBooks.get(position));
    }

    @Override
    public int getItemCount() {
        return chequeBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChequeBookBinding binding;

        ViewHolder(ItemChequeBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(ChequeBookResponse book) {
            binding.bookNumber.setText("Book #" + (book.getBookSerialNumber() != null ? book.getBookSerialNumber() : book.getChequeBookId()));
            binding.bookStatus.setText(book.getStatus().name());
            binding.accountValue.setText(book.getAccountNumber());
            binding.leavesValue.setText(book.getNumberOfLeaves() + " Leaves");
            binding.serialRange.setText("Range: " + book.getStartLeafNumber() + " - " + book.getEndLeafNumber());

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onBookClick(book);
            });
        }
    }
}
