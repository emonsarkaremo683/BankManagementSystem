package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemChequeBinding;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;

import java.util.List;

public class ChequeAdapter extends RecyclerView.Adapter<ChequeAdapter.VH> {
    private final List<ChequeBookResponse> items;
    public ChequeAdapter(List<ChequeBookResponse> items){ this.items=items; }
    public void update(List<ChequeBookResponse> data){ items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int t){ return new VH(ItemChequeBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h,int pos){
        ChequeBookResponse c = items.get(pos);
        h.b.tvBookId.setText("Book #"+c.getChequeBookId());
        h.b.tvLeaves.setText(c.getNumberOfLeaves()+" leaves • "+(c.getAccountNumber()!=null?c.getAccountNumber():""));
        h.b.chipStatus.setText(c.getStatus()!=null?c.getStatus().name():"Active");
    }
    @Override public int getItemCount(){ return items.size(); }
    static class VH extends RecyclerView.ViewHolder{ ItemChequeBinding b; VH(ItemChequeBinding b){ super(b.getRoot()); this.b=b; } }
}
