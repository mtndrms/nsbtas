package com.nsbtas.nsbtas.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;

public class PlaceAmountFragment extends Fragment {
    TextView amount;
    private int serviceId;
    private String firmName;
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private String note;
    private String address;
    private int cardId;

    public PlaceAmountFragment() {
    }

    public static PlaceAmountFragment newInstance() {
        return new PlaceAmountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_amount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        amount = view.findViewById(R.id.tvAmount);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                serviceId = result.getInt("serviceId");
                firmName = result.getString("companyName");
                customerName = result.getString("customerName");
                emailAddress = result.getString("emailAddress");
                address = result.getString("address");
                note = result.getString("note");
                phoneNumber = result.getString("phoneNumber");
                cardId = result.getInt("cardId");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Bundle result = new Bundle();

        result.putInt("serviceId", serviceId);
        result.putString("companyName", firmName);
        result.putString("customerName", customerName);
        result.putString("emailAddress", emailAddress);
        result.putString("address", address);
        result.putString("note", note);
        result.putString("phoneNumber", phoneNumber);
        result.putInt("cardId", cardId);
        result.putString("amount", amount.getText().toString());
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }

    public int getAmount() {
        return Integer.parseInt(amount.getText().toString());
    }
}