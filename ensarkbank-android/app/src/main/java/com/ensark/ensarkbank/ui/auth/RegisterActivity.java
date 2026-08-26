package com.ensark.ensarkbank.ui.auth;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.ensark.ensarkbank.databinding.ActivityRegisterBinding;
import com.ensark.ensarkbank.model.dto.AddressRequest;
import com.ensark.ensarkbank.model.dto.CustomerRequest;
import com.ensark.ensarkbank.model.enums.AddressType;
import com.ensark.ensarkbank.model.enums.CustomerOccupation;
import com.ensark.ensarkbank.model.enums.Gender;
import com.ensark.ensarkbank.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private RegisterViewModel viewModel;
    private final Calendar calendar = Calendar.getInstance();
    private Date selectedDob;

    private Long selectedDivisionId;
    private Long selectedDistrictId;
    private Long selectedPoliceStationId;

    private Long selectedPermDivisionId;
    private Long selectedPermDistrictId;
    private Long selectedPermPoliceStationId;

    private MultipartBody.Part profileImagePart;
    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    binding.profileImage.setImageURI(uri);
                    binding.profileImage.setPadding(0, 0, 0, 0);
                    binding.profileImage.setColorFilter(null);
                    prepareImagePart(uri);
                }
            }
    );

    @Override
    protected ActivityRegisterBinding inflateBinding(LayoutInflater inflater) {
        return ActivityRegisterBinding.inflate(inflater);
    }

    @Override
    protected void onInit() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        setupEnumDropdowns();
        setupDatePicker();
        observeViewModel();

        binding.selectImageFab.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        binding.registerButton.setOnClickListener(v -> handleRegistration());
        binding.loginLink.setOnClickListener(v -> finish());

        binding.sameAsPresentCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.permanentAddressContainer.setVisibility(isChecked ? android.view.View.GONE : android.view.View.VISIBLE);
        });

        viewModel.fetchDivisions();
    }

    private void prepareImagePart(android.net.Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getCacheDir(), "profile_image.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            profileImagePart = MultipartBody.Part.createFormData("profile", file.getName(), requestFile);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupEnumDropdowns() {
        List<String> genders = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            genders.add(formatEnumName(gender.name()));
        }
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                com.ensark.ensarkbank.R.layout.item_dropdown, genders);
        binding.genderDropdown.setAdapter(genderAdapter);

        List<String> occupations = new ArrayList<>();
        for (CustomerOccupation occ : CustomerOccupation.values()) {
            occupations.add(formatEnumName(occ.name()));
        }
        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(this,
                com.ensark.ensarkbank.R.layout.item_dropdown, occupations);
        binding.occupationDropdown.setAdapter(occupationAdapter);
    }

    private String formatEnumName(String name) {
        if (name == null || name.isEmpty()) return "";
        String formatted = name.replace("_", " ").toLowerCase();
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateDobLabel();
        };

        binding.dobEditText.setOnClickListener(v -> new DatePickerDialog(this, date,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateDobLabel() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        selectedDob = calendar.getTime();
        binding.dobEditText.setText(sdf.format(selectedDob));
    }

    private void observeViewModel() {
        viewModel.divisions.observe(this, list -> {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) names.add(list.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.ensark.ensarkbank.R.layout.item_dropdown, names);
            
            binding.divisionDropdown.setAdapter(adapter);
            binding.divisionDropdown.setOnItemClickListener((parent, view, position, id) -> {
                selectedDivisionId = list.get(position).getId();
                binding.districtDropdown.setText("");
                binding.policeStationDropdown.setText("");
                viewModel.fetchDistricts(selectedDivisionId);
            });

            binding.permDivisionDropdown.setAdapter(adapter);
            binding.permDivisionDropdown.setOnItemClickListener((parent, view, position, id) -> {
                selectedPermDivisionId = list.get(position).getId();
                binding.permDistrictDropdown.setText("");
                binding.permPoliceStationDropdown.setText("");
                viewModel.fetchPermDistricts(selectedPermDivisionId);
            });
        });

        viewModel.districts.observe(this, list -> {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) names.add(list.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.ensark.ensarkbank.R.layout.item_dropdown, names);
            
            binding.districtDropdown.setAdapter(adapter);
            binding.districtDropdown.setOnItemClickListener((parent, view, position, id) -> {
                selectedDistrictId = list.get(position).getId();
                binding.policeStationDropdown.setText("");
                viewModel.fetchPoliceStations(selectedDistrictId);
            });
        });

        viewModel.permDistricts.observe(this, list -> {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) names.add(list.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.ensark.ensarkbank.R.layout.item_dropdown, names);

            binding.permDistrictDropdown.setAdapter(adapter);
            binding.permDistrictDropdown.setOnItemClickListener((parent, view, position, id) -> {
                selectedPermDistrictId = list.get(position).getId();
                binding.permPoliceStationDropdown.setText("");
                viewModel.fetchPermPoliceStations(selectedPermDistrictId);
            });
        });

        viewModel.policeStations.observe(this, list -> {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) names.add(list.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.ensark.ensarkbank.R.layout.item_dropdown, names);
            
            binding.policeStationDropdown.setAdapter(adapter);
            binding.policeStationDropdown.setOnItemClickListener((parent, view, position, id) -> {
                selectedPoliceStationId = list.get(position).getId();
            });
        });

        viewModel.permPoliceStations.observe(this, list -> {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) names.add(list.get(i).getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.ensark.ensarkbank.R.layout.item_dropdown, names);

            binding.permPoliceStationDropdown.setAdapter(adapter);
            binding.permPoliceStationDropdown.setOnItemClickListener((parent, view, position, id) -> {
                selectedPermPoliceStationId = list.get(position).getId();
            });
        });

        viewModel.registrationSuccess.observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        viewModel.errorMessage.observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                viewModel.clearError();
            }
        });

        viewModel.isLoading.observe(this, isLoading -> {
            binding.registerButton.setEnabled(!isLoading);
            binding.registerButton.setText(isLoading ? "" : "Register");
            binding.progressBar.setVisibility(isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        });
    }

    private void handleRegistration() {
        try {
            String genderStr = binding.genderDropdown.getText().toString();
            String occupationStr = binding.occupationDropdown.getText().toString();
            
            String genderEnumName = genderStr.replace(" ", "_").toUpperCase();
            String occupationEnumName = occupationStr.replace(" ", "_").toUpperCase();

            List<AddressRequest> addresses = new ArrayList<>();
            
            addresses.add(AddressRequest.builder()
                    .holdingNo(binding.holdingEditText.getText().toString())
                    .area(binding.areaEditText.getText().toString())
                    .postalCode(binding.postalCodeEditText.getText().toString())
                    .addressType(AddressType.PRESENT)
                    .policeStation(AddressRequest.PoliceStationRef.builder().id(selectedPoliceStationId).build())
                    .build());

            if (binding.sameAsPresentCheckbox.isChecked()) {
                addresses.add(AddressRequest.builder()
                        .holdingNo(binding.holdingEditText.getText().toString())
                        .area(binding.areaEditText.getText().toString())
                        .postalCode(binding.postalCodeEditText.getText().toString())
                        .addressType(AddressType.PERMANENT)
                        .policeStation(AddressRequest.PoliceStationRef.builder().id(selectedPoliceStationId).build())
                        .build());
            } else {
                addresses.add(AddressRequest.builder()
                        .holdingNo(binding.permHoldingEditText.getText().toString())
                        .area(binding.permAreaEditText.getText().toString())
                        .postalCode(binding.permPostalCodeEditText.getText().toString())
                        .addressType(AddressType.PERMANENT)
                        .policeStation(AddressRequest.PoliceStationRef.builder().id(selectedPermPoliceStationId).build())
                        .build());
            }

            CustomerRequest request = CustomerRequest.builder()
                    .name(binding.nameEditText.getText().toString())
                    .email(binding.emailEditText.getText().toString())
                    .phone(binding.phoneEditText.getText().toString())
                    .password(binding.passwordEditText.getText().toString())
                    .gender(Gender.valueOf(genderEnumName))
                    .occupation(CustomerOccupation.valueOf(occupationEnumName))
                    .dob(selectedDob)
                    .addresses(addresses)
                    .build();

            viewModel.register(request, profileImagePart);
        } catch (Exception e) {
            Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show();
        }
    }
}
