package com.ensark.ensarkbank.ui.loan;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensark.ensarkbank.databinding.ItemLoanBinding;
import com.ensark.ensarkbank.model.dto.LoanApplicationResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder> {

    private final List<LoanApplicationResponse> loans = new ArrayList<>();
    private OnLoanClickListener listener;

    public interface OnLoanClickListener {
        void onLoanClick(LoanApplicationResponse loan);
    }

    public void setListener(OnLoanClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLoans(List<LoanApplicationResponse> newLoans) {
        loans.clear();
        if (newLoans != null) {
            loans.addAll(newLoans);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoanBinding binding = ItemLoanBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(loans.get(position));
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLoanBinding binding;

        ViewHolder(ItemLoanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(LoanApplicationResponse loan) {
            binding.loanIdText.setText("Loan #" + loan.getLoanId());
            binding.loanStatus.setText(loan.getStatus().name());
            binding.principalValue.setText("৳ " + loan.getPrincipalAmount().toString());
            binding.outstandingValue.setText("৳ " + loan.getOutstandingBalance().toString());
            binding.emiValue.setText("EMI: ৳ " + loan.getEmiAmount().toString());

            if (loan.getNextDueDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                binding.nextDueLabel.setText("Next Payment: " + sdf.format(loan.getNextDueDate()));
            }

            // Simple progress calculation: (Principal - Outstanding) / Principal * 100
            double paid = loan.getPrincipalAmount().subtract(loan.getOutstandingBalance()).doubleValue();
            int progress = (int) ((paid / loan.getPrincipalAmount().doubleValue()) * 100);
            binding.loanProgress.setProgress(progress);

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onLoanClick(loan);
            });
        }
    }
}
