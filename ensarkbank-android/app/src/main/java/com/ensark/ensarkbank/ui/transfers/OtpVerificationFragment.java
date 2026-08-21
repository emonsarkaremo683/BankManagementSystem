package com.ensark.ensarkbank.ui.transfers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.databinding.ActivityOtpVerificationBinding;

public class OtpVerificationFragment extends Fragment {
    private ActivityOtpVerificationBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = ActivityOtpVerificationBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btnVerify.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_otp_to_success));
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
