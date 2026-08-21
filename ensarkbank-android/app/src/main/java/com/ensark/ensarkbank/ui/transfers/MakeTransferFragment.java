package com.ensark.ensarkbank.ui.transfers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.FragmentFormBinding;
import com.ensark.ensarkbank.model.dto.AccountResponse;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;
import com.ensark.ensarkbank.repository.AccountRepository;
import com.ensark.ensarkbank.repository.BeneficiaryRepository;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeTransferFragment extends Fragment {
    private FragmentFormBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentFormBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("Make Transfer");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.btnSubmit.setText("Continue");
        loadData();
        binding.btnSubmit.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("otpReferenceId", "dummy");
            Navigation.findNavController(v).navigate(R.id.action_make_to_otp, b);
        });
    }
    private void loadData() {
        String email = "";
        SessionManager sm = new SessionManager(requireContext());
        if (sm.getUser()!=null && sm.getUser().getName()!=null) email = sm.getUser().getName();
        else if (sm.getCustomer()!=null && sm.getCustomer().getEmail()!=null) email = sm.getCustomer().getEmail();
        new AccountRepository(requireContext()).findByCustomerEmail(email, new Callback<List<AccountResponse>>() {
            @Override public void onResponse(Call<List<AccountResponse>> call, Response<List<AccountResponse>> res) {
                if (res.isSuccessful() && res.body()!=null) {
                    List<String> labels = new ArrayList<>();
                    for (AccountResponse a: res.body()) labels.add(a.getAccountNumber()+" • "+(a.getAccountType()!=null?a.getAccountType().name():""));
                    binding.actDropdown.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, labels));
                }
            }
            @Override public void onFailure(Call<List<AccountResponse>> call, Throwable t) { Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show(); }
        });
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
