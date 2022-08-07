package com.nsbtas.nsbtas.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        TextView btnZero = view.findViewById(R.id.btnZero);
        TextView btnOne = view.findViewById(R.id.btnOne);
        TextView btnTwo = view.findViewById(R.id.btnTwo);
        TextView btnThree = view.findViewById(R.id.btnThree);
        TextView btnFour = view.findViewById(R.id.btnFour);
        TextView btnFive = view.findViewById(R.id.btnFive);
        TextView btnSix = view.findViewById(R.id.btnSix);
        TextView btnSeven = view.findViewById(R.id.btnSeven);
        TextView btnEight = view.findViewById(R.id.btnEight);
        TextView btnNine = view.findViewById(R.id.btnNine);
        TextView btnAllClear = view.findViewById(R.id.btnAllClear);
        ImageView btnDelete = view.findViewById(R.id.btnDelete);

        List<TextView> numpad = Arrays.asList(btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            serviceId = result.getInt("serviceId");
            firmName = result.getString("companyName");
            customerName = result.getString("customerName");
            emailAddress = result.getString("emailAddress");
            address = result.getString("address");
            note = result.getString("note");
            phoneNumber = result.getString("phoneNumber");
            cardId = result.getInt("cardId");
        });

        AtomicInteger dot = new AtomicInteger(3);
        for (TextView button : numpad) {
            button.setOnClickListener(btn -> {
                dot.getAndDecrement();
                amount.setText(amount.getText().toString().concat(button.getText().toString()));
                if (dot.get() == 0) {
                    amount.setText(amount.getText().toString().concat("."));
                    dot.set(3);
                }
            });
        }

        btnAllClear.setOnClickListener(btnAC -> {
            dot.set(3);
            amount.setText(null);
        });

        btnDelete.setOnClickListener(del -> {
            if (amount.getText().toString().length() > 0) {
                amount.setText(amount.getText().toString().substring(0, amount.getText().toString().length() - 1));
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