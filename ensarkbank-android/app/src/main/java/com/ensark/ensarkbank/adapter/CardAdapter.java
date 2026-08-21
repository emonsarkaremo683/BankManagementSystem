package com.ensark.ensarkbank.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ensark.ensarkbank.databinding.ItemCardBinding;
import com.ensark.ensarkbank.model.dto.CardResponse;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.VH> {
    private final List<CardResponse> items;
    private final BindImage binder;
    public interface BindImage { void bind(CardResponse c, ImageView iv); }
    public CardAdapter(List<CardResponse> items, BindImage b) { this.items=items; this.binder=b; }
    public void update(List<CardResponse> data) { items.clear(); items.addAll(data); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t) { return new VH(ItemCardBinding.inflate(LayoutInflater.from(p.getContext()), p, false)); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        CardResponse c = items.get(pos);
        h.b.tvCardName.setText((c.getCardNetwork()!=null?c.getCardNetwork().name():"")+" "+(c.getCardType()!=null?c.getCardType().name():""));
        String num = c.getCardNumber()!=null?c.getCardNumber():"**** **** **** ****";
        if (num.length()>4) num="**** **** **** "+num.substring(Math.max(0,num.length()-4));
        h.b.tvCardNumber.setText(num);
        h.b.chipStatus.setText(c.getStatus()!=null?c.getStatus().name():"");
        h.b.tvLimit.setText("Limit: $"+(c.getDailyLimit()!=null?c.getDailyLimit().toPlainString():"0"));
        if (binder!=null) binder.bind(c, h.b.ivCardImage);
        else Glide.with(h.itemView.getContext()).load(c.getCardNumber()).into(h.b.ivCardImage);
    }
    @Override public int getItemCount() { return items.size(); }
    static class VH extends RecyclerView.ViewHolder { ItemCardBinding b; VH(ItemCardBinding b){ super(b.getRoot()); this.b=b; } }
}
