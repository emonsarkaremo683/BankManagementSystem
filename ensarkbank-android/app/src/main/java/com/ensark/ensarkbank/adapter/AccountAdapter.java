package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemAccountBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.VH> {
    private final List<AccountResponse> items;
    private final OnClick listener;
    public interface OnClick { void onClick(AccountResponse item); }
    public AccountAdapter(List<AccountResponse> items, OnClick l) { this.items=items; this.listener=l; }
    public void update(List<AccountResponse> data) { items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t) { return new VH(ItemAccountBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        AccountResponse a = items.get(pos);
        h.b.tvName.setText(a.getAccountType()!=null?a.getAccountType().name():"Account");
        h.b.tvNumber.setText(a.getAccountNumber()!=null?a.getAccountNumber():"");
        h.b.tvBalance.setText(a.getAvailableBalance()!=null?"$ "+a.getAvailableBalance().toPlainString():"$ 0.00");
        String st = a.getAccountStatus()!=null?a.getAccountStatus().name():"Active";
        h.b.chipStatus.setText(st);
        h.b.getRoot().setOnClickListener(v -> { if(listener!=null) listener.onClick(a); });
    }
    @Override public int getItemCount() { return items.size(); }
    static class VH extends RecyclerView.ViewHolder { ItemAccountBinding b; VH(ItemAccountBinding b){ super(b.getRoot()); this.b=b; } }
}
