package com.nsbtas.nsbtas.fragments;

import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getChosenCard;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getChosenCompany;
import static com.nsbtas.nsbtas.utils.Utils.getServiceById;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;

public class ConfirmAndProceedPaymentFragment extends Fragment {

    public ConfirmAndProceedPaymentFragment() {
    }

    public static ConfirmAndProceedPaymentFragment newInstance() {
        return new ConfirmAndProceedPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_and_proceed_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvService = view.findViewById(R.id.service);
        TextView tvCompanyName = view.findViewById(R.id.tvCompanyName);
        TextView tvCustomerName = view.findViewById(R.id.tvCustomerName);
        TextView tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        TextView tvEmailAddress = view.findViewById(R.id.tvEmailAddress);
        TextView tvNote = view.findViewById(R.id.tvNote);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvCardOwner = view.findViewById(R.id.tvCardOwnerInfo);
        TextView tvCardNumber = view.findViewById(R.id.tvCardNumber);
        TextView tvCardExpDate = view.findViewById(R.id.tvCardExpDate);
        TextView tvCardCVV = view.findViewById(R.id.tvCardCVV);
        TextView tvAmount = view.findViewById(R.id.tvAmount);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            tvService.setText(getServiceById(result.getInt("serviceId")));
            tvCompanyName.setText(getChosenCompany().getCompanyName());
            tvCustomerName.setText(getChosenCompany().getCustomerName());
            tvPhoneNumber.setText(getChosenCompany().getPhoneNumber());
            tvAddress.setText(getChosenCompany().getAddress());
            tvEmailAddress.setText(getChosenCompany().getEmailAddress());
            tvNote.setText(getChosenCompany().getNote());
            tvAmount.setText(result.getString("amount"));
            tvCardOwner.setText(getChosenCard().getCardOwner());
            tvCardNumber.setText(getChosenCard().getCardNumber());
            tvCardExpDate.setText(getChosenCard().getExpirationDate());
            tvCardCVV.setText(getChosenCard().getCVV());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}