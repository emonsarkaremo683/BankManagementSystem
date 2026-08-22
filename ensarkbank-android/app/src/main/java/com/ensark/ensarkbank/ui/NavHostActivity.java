package com.ensark.ensarkbank.ui;

import android.view.LayoutInflater;
import com.ensark.ensarkbank.databinding.ActivityNavHostBinding;
import com.ensark.ensarkbank.ui.base.BaseActivity;

public class NavHostActivity extends BaseActivity<ActivityNavHostBinding> {

    @Override
    protected ActivityNavHostBinding inflateBinding(LayoutInflater inflater) {
        return ActivityNavHostBinding.inflate(inflater);
    }

    @Override
    protected void onInit() {
        // Navigation is handled by NavHostFragment in layout
    }
}
