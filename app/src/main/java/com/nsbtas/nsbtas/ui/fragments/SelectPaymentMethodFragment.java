package com.nsbtas.nsbtas.ui.fragments;

import static com.nsbtas.nsbtas.utils.ExpandableCardListDataPump.getDataList;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCardId;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpandableStackViewAdapter;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.activities.AddNewCardActivity;
import com.nsbtas.nsbtas.ui.views.ExpandableStackView;
import com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper;

import java.util.List;

public class SelectPaymentMethodFragment extends Fragment {
    private int serviceId;
    private String firmName;
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private String note;
    private String address;

    public SelectPaymentMethodFragment() {
    }

    public static SelectPaymentMethodFragment newInstance() {
        return new SelectPaymentMethodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_payment_method, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Card> data = getDataList();

        ExpandableStackView expandableStackView = view.findViewById(R.id.expandable_stack);
        TextView btnAddNewCard = view.findViewById(R.id.tvAddNewCard);

        expandableStackView.setAdapter(new ExpandableStackViewAdapter(data, requireContext(), this, expandableStackView));

        expandableStackView.transitionToEnd();

        btnAddNewCard.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), AddNewCardActivity.class);
            startActivity(intent);
        });

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            serviceId = result.getInt("serviceId");
            firmName = result.getString("companyName");
            customerName = result.getString("customerName");
            emailAddress = result.getString("emailAddress");
            address = result.getString("address");
            note = result.getString("note");
            phoneNumber = result.getString("phoneNumber");
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
        result.putInt("cardId", getCardId());
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}