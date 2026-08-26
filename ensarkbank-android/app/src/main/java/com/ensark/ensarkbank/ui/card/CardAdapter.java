package com.ensark.ensarkbank.ui.card;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ItemCardBinding;
import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.model.enums.CardNetwork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final List<CardResponse> cards = new ArrayList<>();
    private OnCardClickListener listener;

    public interface OnCardClickListener {
        void onCardClick(CardResponse card);
    }

    public void setListener(OnCardClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCards(List<CardResponse> newCards) {
        cards.clear();
        if (newCards != null) {
            cards.addAll(newCards);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCardBinding binding;

        ViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CardResponse card) {
            binding.cardNumber.setText(formatCardNumber(card.getCardNumber()));
            binding.cardHolderName.setText(card.getCardHolderName());
            
            if (card.getExpiryDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yy", Locale.US);
                binding.cardExpiry.setText(sdf.format(card.getExpiryDate()));
            }

            if (card.getCardNetwork() == CardNetwork.VISA) {
                binding.cardBg.setBackgroundResource(R.drawable.bg_card_visa);
                binding.networkLogo.setImageResource(R.drawable.ic_visa);
            } else {
                binding.cardBg.setBackgroundResource(R.drawable.bg_card_mastercard);
                binding.networkLogo.setImageResource(R.drawable.ic_mastercard);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onCardClick(card);
            });
        }

        private String formatCardNumber(String number) {
            if (number == null || number.length() < 16) return number;
            return "**** **** **** " + number.substring(number.length() - 4);
        }
    }
}
