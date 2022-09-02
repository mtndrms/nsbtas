package com.nsbtas.nsbtas.activities;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCurrentPage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.nextStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.previousStage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.reset;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setStages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.utils.Utils;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Utils.setTheme(this);
        setStages();

        TextView tvCancel = findViewById(R.id.tvCancel);
        AppCompatButton btnContinue = findViewById(R.id.btnContinue);
        AppCompatButton btnBack = findViewById(R.id.btnBack);

        tvCancel.setOnClickListener(view -> {
            finish();
        });

        btnContinue.setOnClickListener(view -> {
            if (getCurrentPage() >= 6) {
                finish();
            }
            nextStage(getSupportFragmentManager());
            if (getCurrentPage() == 5) {
                btnContinue.setText(getString(R.string.proceed_payment));
            } else if (getCurrentPage() == 6) {
                btnContinue.setText("Kapat");
            } else {
                btnContinue.setText(getString(R.string.next_stage));
            }
        });

        btnBack.setOnClickListener(view -> {
            previousStage(getSupportFragmentManager());
            btnContinue.setText(getString(R.string.next_stage));
            if (getCurrentPage() <= 0) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }
}