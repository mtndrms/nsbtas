package com.nsbtas.nsbtas.ui.fragments;

import static com.nsbtas.nsbtas.network.Client.getClient;
import static com.nsbtas.nsbtas.utils.ExpandableCardListDataPump.getDataList;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getCardId;
import static com.nsbtas.nsbtas.utils.MultiStepPaymentFormHelper.getTransitionId;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contentful.java.cda.CDAEntry;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpandableStackViewAdapter;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.views.ExpandableStackView;
import com.nsbtas.nsbtas.utils.Callback;

import java.util.ArrayList;
import java.util.List;

public class SelectPaymentMethodFragment extends Fragment {
    private int serviceId;
    private String firmName;
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private String note;
    private String address;

    private final List<Card> cards = new ArrayList<>();
    private Callback callback;

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
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);
        String userEntryId = sharedPreferences.getString("userEntryId", "empty");

        new Thread(() -> {
            List<CDAEntry> cardCdaEntries = getClient().fetch(CDAEntry.class).one(userEntryId).getField("cards");
            for (CDAEntry card : cardCdaEntries) {
                double data = card.getField("id");
                int id = (int) data;
                cards.add(new Card(
                        id,
                        card.getField("provider"),
                        card.getField("cardNumber"),
                        card.getField("cardOwner"),
                        card.getField("expirationDate"),
                        card.getField("cvv")
                ));
            }
            if (callback != null) {
                callback.callback();
            }
        }).start();

        return inflater.inflate(R.layout.fragment_select_payment_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableStackView expandableStackView = view.findViewById(R.id.expandable_stack);

        this.callback = () -> {
            requireActivity().runOnUiThread(() -> {
                expandableStackView.setAdapter(new ExpandableStackViewAdapter(cards, requireContext(), this, expandableStackView));
                expandableStackView.transitionToEnd(() -> {
                    expandableStackView.setTransition(getTransitionId());
                });
            });
        };

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
        cards.clear();

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