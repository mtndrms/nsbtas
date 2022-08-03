package com.nsbtas.nsbtas.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.nsbtas.nsbtas.ExpandableListViewDataPump;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpandableListViewCustomAdapter;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.activities.AddNewCardActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectPaymentMethodFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListViewCustomAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<Card>> expandableListDetail;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvAddNewCard = view.findViewById(R.id.btnAddNewCard);
        expandableListView = view.findViewById(R.id.elvSavedCards);

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
            }
        });

        tvAddNewCard.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddNewCardActivity.class);
            startActivity(intent);
        });

        expandableListDetail = ExpandableListViewDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListViewCustomAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                // FIXME: 3.08.2022 LISTENER IS NOT FIRING!
                return false;
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
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}