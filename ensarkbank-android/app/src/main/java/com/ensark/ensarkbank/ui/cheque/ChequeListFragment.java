package com.ensark.ensarkbank.ui.cheque;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensark.ensarkbank.R;
import com.ensark.ensarkbank.adapter.ChequeAdapter;
import com.ensark.ensarkbank.databinding.FragmentListBinding;
import com.ensark.ensarkbank.model.dto.ChequeBookResponse;
import com.ensark.ensarkbank.repository.ChequeRepository;
import com.ensark.ensarkbank.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChequeListFragment extends Fragment {
    private FragmentListBinding binding;
    private ChequeAdapter adapter;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentListBinding.inflate(inflater, c, false);
        return binding.getRoot();
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolbar.setTitle("Cheque Books");
        adapter = new ChequeAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.fab.setVisibility(View.VISIBLE);
        binding.fab.setText("Apply Cheque");
        binding.fab.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_chequeList_to_apply));
        binding.swipeRefresh.setOnRefreshListener(this::load);
        binding.getRoot().findViewById(R.id.btnRetry).setOnClickListener(v -> load());
        load();
    }
    private void load() {
        binding.swipeRefresh.setRefreshing(true);
        String email = "";
        SessionManager sm = new SessionManager(requireContext());
        if (sm.getUser()!=null && sm.getUser().getName()!=null) email = sm.getUser().getName();
        else if (sm.getCustomer()!=null && sm.getCustomer().getEmail()!=null) email = sm.getCustomer().getEmail();
        new ChequeRepository(requireContext()).findByCustomerEmail(email, new Callback<List<ChequeBookResponse>>() {
            @Override public void onResponse(Call<List<ChequeBookResponse>> call, Response<List<ChequeBookResponse>> res) {
                binding.swipeRefresh.setRefreshing(false);
                if (res.isSuccessful() && res.body()!=null) {
                    adapter.update(res.body());
                    binding.getRoot().findViewById(R.id.emptyState).setVisibility(res.body().isEmpty()?View.VISIBLE:View.GONE);
                    binding.getRoot().findViewById(R.id.errorState).setVisibility(View.GONE);
                } else binding.getRoot().findViewById(R.id.errorState).setVisibility(View.VISIBLE);
            }
            @Override public void onFailure(Call<List<ChequeBookResponse>> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                binding.getRoot().findViewById(R.id.errorState).setVisibility(View.VISIBLE);
            }
        });
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding=null; }
}
