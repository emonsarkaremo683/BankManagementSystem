package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemLoanBinding;
import com.ensark.ensarkbank.model.dto.LoanApplicationResponse;

import java.util.List;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.VH> {
    private final List<LoanApplicationResponse> items;
    public LoanAdapter(List<LoanApplicationResponse> items){ this.items=items; }
    public void update(List<LoanApplicationResponse> data){ items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int t){ return new VH(ItemLoanBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h,int pos){
        LoanApplicationResponse l = items.get(pos);
        h.b.tvLoanType.setText(l.getLoanId()!=null?"Loan #"+l.getLoanId():"Loan");
        h.b.tvAmount.setText(l.getPrincipalAmount()!=null?"$ "+l.getPrincipalAmount().toPlainString():"");
        h.b.tvTenure.setText((l.getTenureMonths()!=null?l.getTenureMonths()+" months":"")+" • "+(l.getAnnualInterestRate()!=null?l.getAnnualInterestRate()+"%":""));
        h.b.chipStatus.setText(l.getStatus()!=null?l.getStatus().name():"Pending");
    }
    @Override public int getItemCount(){ return items.size(); }
    static class VH extends RecyclerView.ViewHolder{ ItemLoanBinding b; VH(ItemLoanBinding b){ super(b.getRoot()); this.b=b; } }
}
