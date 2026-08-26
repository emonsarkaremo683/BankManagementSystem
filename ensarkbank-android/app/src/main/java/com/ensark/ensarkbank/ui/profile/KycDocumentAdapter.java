package com.ensark.ensarkbank.ui.profile;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemKycDocumentBinding;
import com.ensark.ensarkbank.model.dto.KycDocumentResponse;

import java.util.ArrayList;
import java.util.List;

public class KycDocumentAdapter extends RecyclerView.Adapter<KycDocumentAdapter.ViewHolder> {

    private final List<KycDocumentResponse> documents = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setDocuments(List<KycDocumentResponse> newDocs) {
        documents.clear();
        if (newDocs != null) {
            documents.addAll(newDocs);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemKycDocumentBinding binding = ItemKycDocumentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(documents.get(position));
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemKycDocumentBinding binding;

        ViewHolder(ItemKycDocumentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(KycDocumentResponse item) {
            binding.docTypeText.setText(item.getDoc_type().name());
            binding.docStatusText.setText("UPLOADED");
            // Here you could load image if needed using item.getPath()
        }
    }
}
