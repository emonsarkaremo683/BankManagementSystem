package com.ensark.ensarkbank.ui.card;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import com.ensark.ensarkbank.databinding.FragmentCardBinding;
import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.util.List;

public class CardFragment extends BaseFragment<FragmentCardBinding> {

    private CardViewModel viewModel;

    @Override
    protected FragmentCardBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCardBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(CardViewModel.class);

        observeViewModel();

        if (sessionManager.getCustomer() != null) {
            viewModel.fetchCards(sessionManager.getCustomer().getEmail());
        }

        binding.blockSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // In production, trigger OTP or confirmation dialog
                Toast.makeText(requireContext(), "Card Block Request Initiated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        viewModel.cards.observe(getViewLifecycleOwner(), cards -> {
            if (cards != null && !cards.isEmpty()) {
                CardResponse activeCard = cards.get(0);
                binding.previewCardNumber.setText(activeCard.getCardNumber());
                binding.previewHolderName.setText(activeCard.getCardHolderName());
                binding.dailyLimitValue.setText("$ " + activeCard.getDailyLimit().toString());
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }
}
