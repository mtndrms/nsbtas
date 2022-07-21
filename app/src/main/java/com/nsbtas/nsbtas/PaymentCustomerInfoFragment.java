package com.nsbtas.nsbtas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PaymentCustomerInfoFragment extends Fragment {
    public PaymentCustomerInfoFragment() {
    }

    public static PaymentCustomerInfoFragment newInstance() {
        return new PaymentCustomerInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_customer_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout etCompanyName = view.findViewById(R.id.etCompanyName);
        TextInputLayout etCustomerName = view.findViewById(R.id.etCustomerName); // required
        TextInputLayout etPhoneNumber = view.findViewById(R.id.etPhoneNumber); // required
        TextInputLayout etEmailAddress = view.findViewById(R.id.etEmailAddress);
        TextInputLayout etAmount = view.findViewById(R.id.etAmount); // required
        TextInputLayout etNote = view.findViewById(R.id.etNote); // required
        TextInputLayout etPhysicalAddress = view.findViewById(R.id.etPhysicalAddress); // required
        Spinner spServiceType = view.findViewById(R.id.spServiceType);
        AppCompatButton btnContinue = view.findViewById(R.id.btnContinue);
        List<TextInputLayout> requiredFields = Arrays.asList(etCustomerName, etPhoneNumber, etAmount, etNote, etPhysicalAddress);
        String[] services = { "1 Aylık Abonelik", "12 Aylık Abonelik", "nlksoft 3 Yıl E-İmza"};

        spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_spinner_item,services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spServiceType.setAdapter(adapter);

        btnContinue.setOnClickListener(view1 -> {
            if (checkRequiredFields(requiredFields)) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new PaymentCardMethodFragment(), "CARD_FRAGMENT");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public boolean checkRequiredFields(List<TextInputLayout> requiredFields) {
        ArrayList<TextInputLayout> failed = new ArrayList<>();
        for (TextInputLayout field : requiredFields) {
            if (Objects.requireNonNull(field.getEditText()).getText().length() == 0) {
                failed.add(field);
            }
        }
        if (failed.size() > 0) {
            for (TextInputLayout failedField : failed) {
                failedField.setError("Bu alan boş bırakılamaz");
            }
            return false;
        } else {
            return true;
        }
    }
}