package com.ensark.ensarkbank.ui.auth;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.ensark.ensarkbank.databinding.ActivitySplashBinding;
import com.ensark.ensarkbank.ui.NavHostActivity;
import com.ensark.ensarkbank.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected ActivitySplashBinding inflateBinding(LayoutInflater inflater) {
        return ActivitySplashBinding.inflate(inflater);
    }

    @Override
    protected void onInit() {
        startPulseAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (sessionManager.isLoggedIn()) {
                startActivity(new Intent(this, NavHostActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }

    private void startPulseAnimation() {
        Animation pulse = new AlphaAnimation(0.5f, 1.0f);
        pulse.setDuration(1000);
        pulse.setRepeatMode(Animation.REVERSE);
        pulse.setRepeatCount(Animation.INFINITE);
        binding.logo.startAnimation(pulse);
    }
}
