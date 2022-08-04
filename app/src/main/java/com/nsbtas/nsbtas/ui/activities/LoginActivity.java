package com.nsbtas.nsbtas.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS_APP_SETTINGS", Context.MODE_PRIVATE);
        boolean isLightThemeActive = sharedPreferences.getBoolean("isLightThemeActive", true);
        if (isLightThemeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        AppCompatButton btnLogIn = findViewById(R.id.btnLogIn);
        TextInputLayout etUsername = findViewById(R.id.etUsername);
        TextInputLayout etPassword = findViewById(R.id.etPassword);

        btnLogIn.setOnClickListener(view -> {
            if (Objects.requireNonNull(etUsername.getEditText()).getText().toString().equals("test") && Objects.requireNonNull(etPassword.getEditText()).getText().toString().equals("test")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}