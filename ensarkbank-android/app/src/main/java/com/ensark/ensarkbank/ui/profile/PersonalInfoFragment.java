package com.ensark.ensarkbank.ui.profile;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.api.ApiClient;
import com.ensark.ensarkbank.databinding.FragmentPersonalInfoBinding;
import com.ensark.ensarkbank.model.dto.AddressResponse;
import com.ensark.ensarkbank.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PersonalInfoFragment extends BaseFragment<FragmentPersonalInfoBinding> {

    private ProfileViewModel viewModel;

    @Override
    protected FragmentPersonalInfoBinding inflateBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPersonalInfoBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(ProfileViewModel.class);

        observeViewModel();

        if (sessionManager.getCustomer() != null) {
            viewModel.fetchProfile(sessionManager.getCustomer().getEmail());
        }
    }

    @SuppressLint("SetTextI18n")
    private void observeViewModel() {
        viewModel.customerData.observe(getViewLifecycleOwner(), customer -> {
            if (customer != null) {
                binding.nameValue.setText(customer.getName());
                binding.emailValue.setText(customer.getEmail());
                binding.phoneValue.setText(customer.getPhone());
                binding.genderValue.setText(customer.getGender() != null ? customer.getGender().name() : "N/A");
                binding.occupationValue.setText(customer.getOccupation() != null ? customer.getOccupation().name() : "N/A");

                if (customer.getDob() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy", Locale.US);
                    binding.dobValue.setText(sdf.format(customer.getDob()));
                }

                if (customer.getProfile() != null && !customer.getProfile().isEmpty()) {
                    GlideUrl glideUrl = new GlideUrl(ApiClient.IMAGE_URL + customer.getProfile(), new LazyHeaders.Builder()
                            .addHeader("Authorization", "Bearer " + sessionManager.getToken())
                            .build());
                    Glide.with(this)
                            .load(glideUrl)
                            .circleCrop()
                            .placeholder(R.drawable.ic_profile)
                            .into(binding.profileImageLarge);
                }

                if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
                    AddressResponse addr = customer.getAddresses().get(0);
                    binding.addressValue.setText(addr.getHoldingNo() + ", " + addr.getArea() + "\n" +
                            addr.getPoliceStationName() + ", " + addr.getDistrictName() + "\n" +
                            addr.getDivisionName() + " - " + addr.getPostalCode());
                }
            }
        });
    }
}
