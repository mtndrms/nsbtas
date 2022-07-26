package com.nsbtas.nsbtas.activities;

import static com.nsbtas.nsbtas.network.CDAClient.getClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.utils.Utils;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    CDAArray found = null;
    String usernameGiven = "";
    String passwordGiven = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utils.setTheme(this);

        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        AppCompatButton btnLogIn = findViewById(R.id.btnLogIn);
        TextInputLayout etUsername = findViewById(R.id.etUsername);
        TextInputLayout etPassword = findViewById(R.id.etPassword);

        btnLogIn.setOnClickListener(view -> {
            new Thread(() -> {
                try {
                    usernameGiven = Objects.requireNonNull(etUsername.getEditText()).getText().toString();
                    passwordGiven = Objects.requireNonNull(etPassword.getEditText()).getText().toString();
                    found = getClient().fetch(CDAEntry.class)
                            .where("content_type", "user")
                            .where("fields.username", usernameGiven)
                            .where("fields.password", passwordGiven)
                            .all();

                    if (usernameGiven.isEmpty() || passwordGiven.isEmpty()) {
                        runOnUiThread(() -> {
                            if (usernameGiven.isEmpty()) {
                                etUsername.setError("Bu alan boş bırakılamaz");
                            }
                            if (passwordGiven.isEmpty()) {
                                etPassword.setError("Bu alan boş bırakılamaz");
                            }
                        });
                    } else {
                        if (!found.entries().keySet().isEmpty()) {
                            editor.putString("userEntryId", found.entries().keySet().toArray()[0].toString());
                            editor.putString("username", Objects.requireNonNull(found.entries().get(found.entries().keySet().toArray()[0].toString())).getField("username").toString());
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            runOnUiThread(() -> {
                                etUsername.setError("Yanlış kullanıcı adı ya da şifre");
                                etPassword.setError("Yanlış kullanıcı adı ya da şifre");
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}