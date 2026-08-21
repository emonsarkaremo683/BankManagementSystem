package com.ensark.ensarkbank.ui.cheque;

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
import com.ensark.ensarkbank.model.dto.ChequeBookRequest;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;
import com.ensark.ensarkbank.repository.ChequeRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChequeApplyFragment extends Fragment {
    private FragmentFormBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentFormBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("Apply Cheque Book");
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        String[] leaves = new String[]{"10","25","50","100"};
        binding.actDropdown.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, leaves));
        binding.btnSubmit.setOnClickListener(v -> {
            ChequeBookRequest req = new ChequeBookRequest();
            new ChequeRepository(requireContext()).apply(req, new Callback<ChequeBookResponse>() {
                @Override public void onResponse(Call<ChequeBookResponse> call, Response<ChequeBookResponse> res) {
                    if (res.isSuccessful()) {
                        Toast.makeText(requireContext(),"Applied",Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigateUp();
                    } else Toast.makeText(requireContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
                @Override public void onFailure(Call<ChequeBookResponse> call, Throwable t) { Toast.makeText(requireContext(),t.getMessage(),Toast.LENGTH_SHORT).show(); }
            });
        });
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
