package com.nsbtas.nsbtas.ui.fragments;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCurrentPage;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.setRequiredFields;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomerInformationFragment extends Fragment {
    TextInputLayout etCompanyName;
    TextInputLayout etCustomerName;
    TextInputLayout etPhoneNumber;
    TextInputLayout etEmailAddress;
    TextInputLayout etNote;
    TextInputLayout etPhysicalAddress;

    private int serviceId;

    public CustomerInformationFragment() {

    }

    public static CustomerInformationFragment newInstance() {
        return new CustomerInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                serviceId = bundle.getInt("serviceId");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etCompanyName = view.findViewById(R.id.etCompanyName);
        etCustomerName = view.findViewById(R.id.etCustomerName); // required
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber); // required
        etEmailAddress = view.findViewById(R.id.etEmailAddress);
        etNote = view.findViewById(R.id.etNote); // required
        etPhysicalAddress = view.findViewById(R.id.etPhysicalAddress); // required
        List<TextInputLayout> requiredFields = Arrays.asList(etCustomerName, etPhoneNumber, etNote, etPhysicalAddress);

        setRequiredFields(getCurrentPage(), requiredFields);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Bundle result = new Bundle();

        result.putInt("serviceId", serviceId);
        result.putString("companyName", etCompanyName.getEditText().getText().toString());
        result.putString("customerName", etCustomerName.getEditText().getText().toString());
        result.putString("emailAddress", etEmailAddress.getEditText().getText().toString());
        result.putString("address", etPhysicalAddress.getEditText().getText().toString());
        result.putString("note", etNote.getEditText().getText().toString());
        result.putString("phoneNumber", etPhoneNumber.getEditText().getText().toString());
        getParentFragmentManager().setFragmentResult("requestKey", result);
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

    public String getCompanyName() {
        return etCompanyName.getEditText().getText().toString();
    }

    public String getCustomerName() {
        return etCustomerName.getEditText().getText().toString();
    }

    public String getEmailAddress() {
        return etEmailAddress.getEditText().getText().toString();
    }

    public String getPhoneNumber() {
        return etPhoneNumber.getEditText().getText().toString();
    }

    public String getPhysicalAddress() {
        return etPhysicalAddress.getEditText().getText().toString();
    }

    public String getNote() {
        return etNote.getEditText().getText().toString();
    }
}