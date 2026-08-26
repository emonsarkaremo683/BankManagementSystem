package com.ensark.ensarkbank.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ensark.ensarkbank.session.SessionManager;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    protected VB binding;
    protected SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = inflateBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(requireContext());
        onInit();
        setupRefreshLayout();
    }

    protected abstract VB inflateBinding(LayoutInflater inflater, ViewGroup container);
    protected abstract void onInit();

    protected void onRefresh() {
        // To be overridden by subclasses
    }

    private void setupRefreshLayout() {
        View root = binding.getRoot();
        SwipeRefreshLayout swipeRefreshLayout = null;
        
        if (root instanceof SwipeRefreshLayout) {
            swipeRefreshLayout = (SwipeRefreshLayout) root;
        } else if (root instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) root;
            for (int i = 0; i < vg.getChildCount(); i++) {
                if (vg.getChildAt(i) instanceof SwipeRefreshLayout) {
                    swipeRefreshLayout = (SwipeRefreshLayout) vg.getChildAt(i);
                    break;
                }
            }
        }

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
            // Optional: Customize colors
            swipeRefreshLayout.setColorSchemeResources(com.ensark.ensarkbank.R.color.electric_cyan);
            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(com.ensark.ensarkbank.R.color.primary_background);
        }
    }

    protected void setRefreshing(boolean refreshing) {
        View root = binding.getRoot();
        SwipeRefreshLayout swipeRefreshLayout = null;
        if (root instanceof SwipeRefreshLayout) {
            swipeRefreshLayout = (SwipeRefreshLayout) root;
        } else if (root instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) root;
            for (int i = 0; i < vg.getChildCount(); i++) {
                if (vg.getChildAt(i) instanceof SwipeRefreshLayout) {
                    swipeRefreshLayout = (SwipeRefreshLayout) vg.getChildAt(i);
                    break;
                }
            }
        }
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
