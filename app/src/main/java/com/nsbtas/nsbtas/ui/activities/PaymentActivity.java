package com.nsbtas.nsbtas.ui.activities;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCurrentPage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCurrentStageTitle;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.nextStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.previousStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setStages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;
import com.nsbtas.nsbtas.utils.Utils;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Utils.setTheme(this);
        setStages();

        TextView tvCancel = findViewById(R.id.tvCancel);
        TextView tvIndicator = findViewById(R.id.tvIndicator);
        AppCompatButton btnContinue = findViewById(R.id.btnContinue);
        AppCompatButton btnBack = findViewById(R.id.btnBack);

        tvCancel.setOnClickListener(view -> {
            finish();
            MultiStepPaymentFormHelper.reset();
        });

        btnContinue.setOnClickListener(view -> {
            nextStage(getSupportFragmentManager());
            if (MultiStepPaymentFormHelper.getCurrentPage() == 5) {
                btnContinue.setText(getString(R.string.proceed_payment));
            }
            tvIndicator.setText(getCurrentStageTitle());
        });

        btnBack.setOnClickListener(view -> {
            previousStage(getSupportFragmentManager());
            tvIndicator.setText(getCurrentStageTitle());
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