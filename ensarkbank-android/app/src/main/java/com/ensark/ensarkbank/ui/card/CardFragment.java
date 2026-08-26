package com.ensark.ensarkbank.ui.card;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentCardBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.CardRequest;
import com.ensark.ensarkbank.model.dto.CardResponse;
import com.ensark.ensarkbank.model.enums.CardNetwork;
import com.ensark.ensarkbank.model.enums.CardType;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class CardFragment extends BaseFragment<FragmentCardBinding> {

    private CardViewModel viewModel;
    private CardAdapter adapter;
    private List<AccountResponse> accountList = new ArrayList<>();
    private Long selectedAccountId;

    @Override
    protected FragmentCardBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCardBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(CardViewModel.class);
        adapter = new CardAdapter();

        setupRecyclerView();
        observeViewModel();

        binding.applyNewCardButton.setOnClickListener(v -> showApplyCardDialog());

        fetchData();
    }

    private void fetchData() {
        if (sessionManager.getCustomer() != null) {
            viewModel.fetchCards(sessionManager.getCustomer().getEmail());
            viewModel.fetchAccounts(sessionManager.getCustomer().getEmail());
        }
    }

    @Override
    protected void onRefresh() {
        fetchData();
    }

    private void setupRecyclerView() {
        binding.cardRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.cardRecyclerView.setAdapter(adapter);

        adapter.setListener(this::displayCardDetails);
    }

    private void observeViewModel() {
        viewModel.cards.observe(getViewLifecycleOwner(), cards -> {
            setRefreshing(false);
            if (cards != null && !cards.isEmpty()) {
                adapter.setCards(cards);
                displayCardDetails(cards.get(0));
            } else {
                binding.detailsContainer.setVisibility(View.GONE);
            }
        });

        viewModel.accounts.observe(getViewLifecycleOwner(), accounts -> {
            if (accounts != null) accountList = accounts;
        });

        viewModel.cardApplied.observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Card application submitted!", Toast.LENGTH_LONG).show();
                fetchData();
            }
        });

        viewModel.errorMessage.observe(getViewLifecycleOwner(), error -> {
            setRefreshing(false);
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                viewModel.clearError();
            }
        });
    }

    private void displayCardDetails(CardResponse card) {
        binding.detailsContainer.setVisibility(View.VISIBLE);
        binding.dailyLimitValue.setText(getString(R.string.currency_format, card.getDailyLimit().toString()));
        binding.onlineSwitch.setChecked(card.isOnlineTransactionEnabled());
        binding.intlSwitch.setChecked(card.isInternationalEnabled());
        binding.blockSwitch.setChecked(card.getStatus() != com.ensark.ensarkbank.model.enums.CardStatus.ACTIVE);
    }

    private void showApplyCardDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_apply_card, null);
        builder.setView(view);
        android.app.AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        android.widget.AutoCompleteTextView accDropdown = view.findViewById(R.id.accountAutoComplete);
        android.widget.AutoCompleteTextView networkDropdown = view.findViewById(R.id.networkAutoComplete);
        android.widget.AutoCompleteTextView typeDropdown = view.findViewById(R.id.typeAutoComplete);
        android.widget.EditText pinEdit = view.findViewById(R.id.pinEditText);
        android.widget.Button applyBtn = view.findViewById(R.id.applyButton);

        // Accounts
        List<String> accNames = new ArrayList<>();
        for (AccountResponse a : accountList) accNames.add(a.getAccountNumber());
        accDropdown.setAdapter(new ArrayAdapter<>(requireContext(), com.ensark.ensarkbank.R.layout.item_dropdown, accNames));
        accDropdown.setOnItemClickListener((p, v, pos, id) -> selectedAccountId = accountList.get(pos).getId());

        // Network
        networkDropdown.setAdapter(new ArrayAdapter<>(requireContext(), com.ensark.ensarkbank.R.layout.item_dropdown, CardNetwork.values()));
        
        // Type
        typeDropdown.setAdapter(new ArrayAdapter<>(requireContext(), com.ensark.ensarkbank.R.layout.item_dropdown, CardType.values()));

        applyBtn.setOnClickListener(v -> {
            String pin = pinEdit.getText().toString();
            String network = networkDropdown.getText().toString();
            String type = typeDropdown.getText().toString();

            if (selectedAccountId == null || pin.isEmpty() || network.isEmpty() || type.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            CardRequest request = CardRequest.builder()
                    .accountId(selectedAccountId)
                    .cardNetwork(CardNetwork.valueOf(network))
                    .cardType(CardType.valueOf(type))
                    .pin(pin)
                    .isOnlineTransactionEnabled(true)
                    .isInternationalEnabled(false)
                    .build();

            viewModel.applyForCard(request);
            dialog.dismiss();
        });

        dialog.show();
    }
}
