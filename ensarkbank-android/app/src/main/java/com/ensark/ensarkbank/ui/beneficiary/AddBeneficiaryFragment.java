package com.ensark.ensarkbank.ui.beneficiary;

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

import com.ensark.ensarkbank.databinding.FragmentFormBinding;
import com.ensark.ensarkbank.model.dto.BeneficiaryRequest;
import com.ensark.ensarkbank.model.dto.BeneficiaryResponse;
import com.ensark.ensarkbank.repository.BeneficiaryRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBeneficiaryFragment extends Fragment {
    private FragmentFormBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentFormBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("Add Beneficiary");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        String[] types = new String[]{"BANK","INTER_BANK"};
        binding.actDropdown.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, types));
        binding.btnSubmit.setText("Save Beneficiary");
        binding.btnSubmit.setOnClickListener(v -> {
            BeneficiaryRequest req = new BeneficiaryRequest();
            new BeneficiaryRepository(requireContext()).add(req, new Callback<BeneficiaryResponse>() {
                @Override public void onResponse(Call<BeneficiaryResponse> call, Response<BeneficiaryResponse> res) {
                    if (res.isSuccessful()) {
                        Toast.makeText(requireContext(),"Beneficiary added",Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigateUp();
                    } else Toast.makeText(requireContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
                @Override public void onFailure(Call<BeneficiaryResponse> call, Throwable t) { Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_SHORT).show(); }
            });
        });
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
