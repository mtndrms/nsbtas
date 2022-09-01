package com.nsbtas.nsbtas.fragments;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nsbtas.nsbtas.R;

public class TransactionResultFragment extends Fragment {
    public TransactionResultFragment() {
    }

    public static TransactionResultFragment newInstance(String param1, String param2) {
        return new TransactionResultFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView done = view.findViewById(R.id.ivDone);
        ConstraintLayout container = view.findViewById(R.id.container);
        AnimatedVectorDrawable animatedVectorDrawable;
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat;

        container.setOnClickListener(con -> {
            requireActivity().finish();
        });

        Drawable drawable = done.getDrawable();
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            animatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) drawable;
            animatedVectorDrawableCompat.start();
        } else if (drawable instanceof AnimatedVectorDrawable) {
            animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
            animatedVectorDrawable.start();
        }
    }
}