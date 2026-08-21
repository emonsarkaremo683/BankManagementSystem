package com.ensark.ensarkbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.ensark.ensarkbank.session.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ensark.ensarkbank.R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(this::navigate, 1400);
    }

    private void navigate() {
        SessionManager session = new SessionManager(this);
        Class<?> target = session.isLoggedIn() ? MainActivity.class : LoginActivity.class;
        startActivity(new Intent(this, target));
        finish();
    }
}
