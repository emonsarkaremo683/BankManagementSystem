package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ItemBeneficiaryBinding;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;

import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.VH> {
    private final List<BeneficiaryResponse> items;
    private final BindImage binder;
    public interface BindImage { void bind(BeneficiaryResponse r, ImageView iv); }
    public BeneficiaryAdapter(List<BeneficiaryResponse> items, BindImage b) { this.items=items; this.binder=b; }
    public void update(List<BeneficiaryResponse> data) { items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p,int t){ return new VH(ItemBeneficiaryBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos){
        BeneficiaryResponse r = items.get(pos);
        h.b.tvName.setText(r.getName());
        h.b.tvAccount.setText((r.getAccNumber()!=null?r.getAccNumber():"")+" • "+(r.getProvider()!=null?r.getProvider():""));
        boolean verified = r.isVerified();
        h.b.chipStatus.setText(verified?"Verified":"Pending");
        h.b.chipStatus.setChipBackgroundColorResource(verified?R.color.success_soft:R.color.warning_soft);
        h.b.chipStatus.setTextColor(h.itemView.getContext().getColor(verified?R.color.success:R.color.warning));
        if (binder!=null) binder.bind(r, h.b.ivAvatar);
    }
    @Override public int getItemCount(){ return items.size(); }
    static class VH extends RecyclerView.ViewHolder{ ItemBeneficiaryBinding b; VH(ItemBeneficiaryBinding b){ super(b.getRoot()); this.b=b; } }
}
