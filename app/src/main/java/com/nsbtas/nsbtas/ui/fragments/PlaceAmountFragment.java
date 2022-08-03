package com.nsbtas.nsbtas.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;

public class PlaceAmountFragment extends Fragment {
    TextView amount;

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
    }

    public int getAmount() {
        return Integer.parseInt(amount.getText().toString());
    }
}