package com.nsbtas.nsbtas.ui.fragments;

import static com.nsbtas.nsbtas.ExpandableListViewDataPump.getDataList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.adapters.ExpandableStackViewAdapter;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.components.ExpandableStackView;

import java.util.List;

public class SelectPaymentMethodFragment extends Fragment {
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

        List<Card> tmp = getDataList();

        ExpandableStackView expandableStackView = view.findViewById(R.id.expandable_stack);
        ImageView btnExpend = view.findViewById(R.id.ivExpend);

        expandableStackView.setAdapter(new ExpandableStackViewAdapter(tmp, requireContext()));

        btnExpend.setOnClickListener(button -> {
            if (expandableStackView.getCurrentState() == expandableStackView.getStartState()) {
                expandableStackView.transitionToEnd();
            } else {
                expandableStackView.transitionToStart();
            }
        });
    }
}