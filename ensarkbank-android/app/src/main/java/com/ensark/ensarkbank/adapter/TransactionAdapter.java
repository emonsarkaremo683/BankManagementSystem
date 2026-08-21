package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ItemTransactionBinding;
import com.ensark.ensarkbank.model.dto.JournalResponse;
import com.ensark.ensarkbank.model.enums.EntryType;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.VH> {
    private final List<JournalResponse> items;
    public TransactionAdapter(List<JournalResponse> items) { this.items=items; }
    public void update(List<JournalResponse> data) { items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t) { return new VH(ItemTransactionBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        JournalResponse j = items.get(pos);
        h.b.tvTitle.setText(j.getCounterpartyName()!=null?j.getCounterpartyName():j.getParticulars()!=null?j.getParticulars():"Transaction");
        h.b.tvSubtitle.setText(j.getTransactionType()!=null?j.getTransactionType().name():"");
        String amt = j.getAmount()!=null?j.getAmount().toPlainString():"0.00";
        boolean credit = j.getEntryType()==EntryType.CREDIT;
        h.b.tvAmount.setText((credit?"+ $ ":"- $ ")+amt);
        h.b.tvAmount.setTextColor(h.itemView.getContext().getColor(credit?R.color.success:R.color.danger));
        h.b.iconBg.setCardBackgroundColor(h.itemView.getContext().getColor(credit?R.color.success_soft:R.color.danger_soft));
        h.b.chipStatus.setText(j.getStatus()!=null?j.getStatus().name():"");
        if (j.getStatus()!=null) {
            switch (j.getStatus().name()) {
                case "SUCCESS": h.b.chipStatus.setChipBackgroundColorResource(R.color.success_soft); h.b.chipStatus.setTextColor(h.itemView.getContext().getColor(R.color.success)); break;
                case "PENDING": h.b.chipStatus.setChipBackgroundColorResource(R.color.warning_soft); h.b.chipStatus.setTextColor(h.itemView.getContext().getColor(R.color.warning)); break;
                default: h.b.chipStatus.setChipBackgroundColorResource(R.color.danger_soft); h.b.chipStatus.setTextColor(h.itemView.getContext().getColor(R.color.danger)); break;
            }
        }
    }
    @Override public int getItemCount() { return items.size(); }
    static class VH extends RecyclerView.ViewHolder { ItemTransactionBinding b; VH(ItemTransactionBinding b){ super(b.getRoot()); this.b=b; } }
}
