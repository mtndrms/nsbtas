package com.nsbtas.nsbtas.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.ui.activities.AddNewCardActivity;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("NSBTAS_APP_SETTINGS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isLightThemeActive = sharedPreferences.getBoolean("isLightThemeActive", true);
        ImageView btnChangeTheme = view.findViewById(R.id.changeTheme);
        AppCompatButton btnAddNewCard = view.findViewById(R.id.btnAddNewCard);

        if (isLightThemeActive) {
            btnChangeTheme.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_dark_mode));
        } else {
            btnChangeTheme.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_light_mode));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                btnChangeTheme.setImageTintList(ContextCompat.getColorStateList(requireContext(), R.color.icon_tint_color));
            }
        }

        btnChangeTheme.setOnClickListener(view1 -> {
            if (isLightThemeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("isLightThemeActive", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("isLightThemeActive", true);
            }
            editor.apply();
        });

        btnAddNewCard.setOnClickListener(btnAdd -> {
            Intent intent = new Intent(requireContext(), AddNewCardActivity.class);
            startActivity(intent);
        });
    }
}