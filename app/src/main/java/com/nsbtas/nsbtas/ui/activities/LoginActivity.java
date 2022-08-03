package com.nsbtas.nsbtas.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));

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