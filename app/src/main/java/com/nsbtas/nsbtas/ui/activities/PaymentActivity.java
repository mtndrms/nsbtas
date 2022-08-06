package com.nsbtas.nsbtas.ui.activities;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.nextStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.previousStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setStages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.nsbtas.nsbtas.ui.fragments.CustomerInformationFragment;
import com.nsbtas.nsbtas.ui.fragments.ConfirmAndProceedPaymentFragment;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.ui.fragments.ChooseServiceFragment;
import com.nsbtas.nsbtas.ui.fragments.PlaceAmountFragment;
import com.nsbtas.nsbtas.ui.fragments.SelectPaymentMethodFragment;
import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setStages();

        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS_APP_SETTINGS", Context.MODE_PRIVATE);
        boolean isLightThemeActive = sharedPreferences.getBoolean("isLightThemeActive", true);
        if (isLightThemeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        TextView tvCancel = findViewById(R.id.tvCancel);
        TextView tvIndicator = findViewById(R.id.tvIndicator);
        AppCompatButton btnContinue = findViewById(R.id.btnContinue);
        AppCompatButton btnBack = findViewById(R.id.btnBack);

        tvCancel.setOnClickListener(view -> finish());

        btnContinue.setOnClickListener(view -> {
            nextStage(getSupportFragmentManager());
            if (MultiStepPaymentFormHelper.getCurrentPage() == 5) {
                btnContinue.setText(getString(R.string.proceed_payment));
            }
        });

        btnBack.setOnClickListener(view -> {
            previousStage(getSupportFragmentManager());
            btnContinue.setText(getString(R.string.next_stage));
        });
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = PaymentActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }
}