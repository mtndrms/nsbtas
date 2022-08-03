package com.nsbtas.nsbtas.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nsbtas.nsbtas.ui.fragments.CustomerInformationFragment;
import com.nsbtas.nsbtas.ui.fragments.ConfirmAndProceedPaymentFragment;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.ui.fragments.ChooseServiceFragment;
import com.nsbtas.nsbtas.ui.fragments.PlaceAmountFragment;
import com.nsbtas.nsbtas.ui.fragments.SelectPaymentMethodFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentActivity extends AppCompatActivity {

    private int serviceChosen = 0;
    private String firmName;
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private int amount;
    private String note;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));

        TextView tvCancel = findViewById(R.id.tvCancel);
        TextView tvIndicator = findViewById(R.id.tvIndicator);
        AppCompatButton btnContinue = findViewById(R.id.btnContinue);
        AppCompatButton btnBack = findViewById(R.id.btnBack);
        HashMap<Integer, Fragment> stages = new HashMap<>();

        AtomicInteger currentPage = new AtomicInteger(1);
        stages.put(1, new ChooseServiceFragment());
        stages.put(2, new CustomerInformationFragment());
        stages.put(3, new SelectPaymentMethodFragment());
        stages.put(4, new PlaceAmountFragment());
        stages.put(5, new ConfirmAndProceedPaymentFragment());

        tvCancel.setOnClickListener(view -> finish());

        btnContinue.setOnClickListener(view -> {
            // passData(currentPage.get());
            if (currentPage.get() < 5) {
                currentPage.getAndIncrement();
                changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())));
                tvIndicator.setText(String.format("%d/%d", currentPage.get(), stages.size()));
            }
            if (currentPage.get() == 5) {
                btnContinue.setText("Ödemeyi Tamamla");
            }
        });

        btnBack.setOnClickListener(view -> {
            if (currentPage.get() > 1) {
                currentPage.getAndDecrement();
                changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())));
                tvIndicator.setText(String.format("%d/%d", currentPage.get(), stages.keySet().size()));
            }
            btnContinue.setText("Devam Et");
        });
    }

    private void changeFragment(String tag, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    /*
    private void passData(int currentPage) {
        switch (currentPage) {
            case 1:
                ChooseServiceFragment chooseServiceFragment = (ChooseServiceFragment) getVisibleFragment();
                this.serviceChosen = chooseServiceFragment.getServiceChosen();
                break;
            case 2:
                CustomerInformationFragment customerInformationFragment = (CustomerInformationFragment) getVisibleFragment();
                this.firmName = customerInformationFragment.getCompanyName();
                this.customerName = customerInformationFragment.getCustomerName();
                this.phoneNumber = customerInformationFragment.getPhoneNumber();
                this.emailAddress = customerInformationFragment.getEmailAddress();
                this.address = customerInformationFragment.getPhysicalAddress();
                //this.amount = customerInformationFragment.getAmount();
                this.note = customerInformationFragment.getNote();
                break;
            case 3:
                // How to get which card is selected?
                break;
            case 4:
                PlaceAmountFragment placeAmountFragment = (PlaceAmountFragment) getVisibleFragment();
                this.amount = placeAmountFragment.getAmount();
                break;
            case 5:
                ConfirmAndProceedPaymentFragment confirmAndProceedPaymentFragment = (ConfirmAndProceedPaymentFragment) getVisibleFragment();
                break;
        }
    }
     */

    public void setServiceChosen(int serviceChosen) {
        this.serviceChosen = serviceChosen;
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