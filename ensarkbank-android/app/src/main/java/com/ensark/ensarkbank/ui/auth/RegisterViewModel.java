package com.ensark.ensarkbank.ui.auth;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensark.ensarkbank.model.dto.CustomerRequest;
import com.ensark.ensarkbank.model.dto.CustomerResponse;
import com.ensark.ensarkbank.model.dto.DivisionResponse;
import com.ensark.ensarkbank.model.dto.DistrictResponse;
import com.ensark.ensarkbank.model.dto.PoliceStationResponse;
import com.ensark.ensarkbank.repository.AuthRepository;
import com.ensark.ensarkbank.repository.GeneralRepository;
import com.ensark.ensarkbank.ui.base.BaseViewModel;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends BaseViewModel {

    private final AuthRepository authRepository;
    private final GeneralRepository generalRepository;

    private final MutableLiveData<List<DivisionResponse>> _divisions = new MutableLiveData<>();
    public final LiveData<List<DivisionResponse>> divisions = _divisions;

    private final MutableLiveData<List<DistrictResponse>> _districts = new MutableLiveData<>();
    public final LiveData<List<DistrictResponse>> districts = _districts;

    private final MutableLiveData<List<PoliceStationResponse>> _policeStations = new MutableLiveData<>();
    public final LiveData<List<PoliceStationResponse>> policeStations = _policeStations;

    private final MutableLiveData<List<DistrictResponse>> _permDistricts = new MutableLiveData<>();
    public final LiveData<List<DistrictResponse>> permDistricts = _permDistricts;

    private final MutableLiveData<List<PoliceStationResponse>> _permPoliceStations = new MutableLiveData<>();
    public final LiveData<List<PoliceStationResponse>> permPoliceStations = _permPoliceStations;

    private final MutableLiveData<Boolean> _registrationSuccess = new MutableLiveData<>();
    public final LiveData<Boolean> registrationSuccess = _registrationSuccess;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = new AuthRepository(application);
        this.generalRepository = new GeneralRepository(application);
    }

    public void fetchDivisions() {
        generalRepository.getAllDivisions(new Callback<List<DivisionResponse>>() {
            @Override
            public void onResponse(Call<List<DivisionResponse>> call, Response<List<DivisionResponse>> response) {
                if (response.isSuccessful()) _divisions.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DivisionResponse>> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    public void fetchDistricts(Long divisionId) {
        generalRepository.getDistrictsByDivision(divisionId, new Callback<List<DistrictResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                if (response.isSuccessful()) _districts.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    public void fetchPoliceStations(Long districtId) {
        generalRepository.getPoliceStationsByDistrict(districtId, new Callback<List<PoliceStationResponse>>() {
            @Override
            public void onResponse(Call<List<PoliceStationResponse>> call, Response<List<PoliceStationResponse>> response) {
                if (response.isSuccessful()) _policeStations.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PoliceStationResponse>> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    public void fetchPermDistricts(Long divisionId) {
        generalRepository.getDistrictsByDivision(divisionId, new Callback<List<DistrictResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                if (response.isSuccessful()) _permDistricts.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    public void fetchPermPoliceStations(Long districtId) {
        generalRepository.getPoliceStationsByDistrict(districtId, new Callback<List<PoliceStationResponse>>() {
            @Override
            public void onResponse(Call<List<PoliceStationResponse>> call, Response<List<PoliceStationResponse>> response) {
                if (response.isSuccessful()) _permPoliceStations.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PoliceStationResponse>> call, Throwable t) {
                setError(t.getMessage());
            }
        });
    }

    public void register(CustomerRequest request, MultipartBody.Part profileImage) {
        setLoading(true);
        String json = com.ensark.ensarkbank.api.ApiClient.getGson().toJson(request);
        RequestBody data = RequestBody.create(MediaType.parse("application/json"), json);

        authRepository.register(data, profileImage, null, null, null, null, new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    _registrationSuccess.postValue(true);
                } else {
                    setError("Registration failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                setLoading(false);
                setError(t.getMessage());
            }
        });
    }
}
