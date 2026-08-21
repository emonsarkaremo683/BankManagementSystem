package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemStandingOrderBinding;
import com.ensark.ensarkbank.model.dto.StandingOrderResponse;

import java.util.List;

public class StandingOrderAdapter extends RecyclerView.Adapter<StandingOrderAdapter.VH> {
    private final List<StandingOrderResponse> items;
    public StandingOrderAdapter(List<StandingOrderResponse> items){ this.items=items; }
    public void update(List<StandingOrderResponse> data){ items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int t){ return new VH(ItemStandingOrderBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h,int pos){
        StandingOrderResponse s = items.get(pos);
        h.b.tvTitle.setText(s.getDescription()!=null?s.getDescription():"Standing Order");
        h.b.tvAmount.setText((s.getAmount()!=null?"$ "+s.getAmount().toPlainString():"")+" • "+(s.getFrequency()!=null?s.getFrequency().name():""));
        h.b.chipStatus.setText(s.getStatus()!=null?s.getStatus().name():"Active");
    }
    @Override public int getItemCount(){ return items.size(); }
    static class VH extends RecyclerView.ViewHolder{ ItemStandingOrderBinding b; VH(ItemStandingOrderBinding b){ super(b.getRoot()); this.b=b; } }
}
