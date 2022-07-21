package com.nsbtas.nsbtas;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PaymentCardMethodFragment extends Fragment {
    boolean isFront = true;
    boolean isTransactionAlreadyHappened = false;

    public PaymentCardMethodFragment() {
    }

    public static PaymentCardMethodFragment newInstance() {
        return new PaymentCardMethodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_card_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout etCardNumber = view.findViewById(R.id.etCardNumber);
        TextInputLayout etCardOwnerInfo = view.findViewById(R.id.etCardOwnerInfo);
        TextInputLayout etExpirationDate = view.findViewById(R.id.etExpirationDate);
        TextInputLayout etCVV = view.findViewById(R.id.etCVV);
        TextView tvCardNumber = view.findViewById(R.id.tvCardNumber);
        TextView tvCardOwnerInfo = view.findViewById(R.id.tvCardOwnerInfo);
        TextView tvExpirationDate = view.findViewById(R.id.tvExpirationDate);
        TextView tvCVV = view.findViewById(R.id.tvCVV);
        LinearLayout cardFront = view.findViewById(R.id.cardFront);
        LinearLayout cardBack = view.findViewById(R.id.cardBack);
        ImageView ivLogo = view.findViewById(R.id.ivLogo);
        AppCompatButton btnProceedPayment = view.findViewById(R.id.btnProceedPayment);

        List<TextInputLayout> requiredFields = Arrays.asList(etCardNumber, etCardOwnerInfo, etExpirationDate, etCVV);

        AnimatorSet front_animation = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_turn_front_animator);
        AnimatorSet back_animation = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_turn_back_animator);

        btnProceedPayment.setOnClickListener(view1 -> {
            if (checkRequiredFields(requiredFields)) {
                Toast.makeText(requireContext(), "ÖDEME TAMAMLANDI!", Toast.LENGTH_LONG).show();
            }
        });

        Objects.requireNonNull(etCardNumber.getEditText()).addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19;

            private static final int TOTAL_DIGITS = 16;
            private static final int DIVIDER_MODULO = 5;
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1;
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!isFront) {
                    front_animation.setTarget(cardBack);
                    back_animation.setTarget(cardFront);
                    back_animation.start();
                    front_animation.start();
                    isFront = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TransitionDrawable transition = (TransitionDrawable) cardFront.getBackground();
                if (s.toString().startsWith("4")) {
                    ivLogo.setImageResource(R.drawable.visa);
                    if (!isTransactionAlreadyHappened) {
                        transition.startTransition(500);
                        isTransactionAlreadyHappened = true;
                    }
                } else if (s.toString().startsWith("5")) {
                    ivLogo.setImageResource(R.drawable.mastercard);
                    if (!isTransactionAlreadyHappened) {
                        transition.startTransition(500);
                        isTransactionAlreadyHappened = true;
                    }
                } else {
                    ivLogo.setImageDrawable(null);
                    if (isTransactionAlreadyHappened) {
                        transition.reverseTransition(500);
                        isTransactionAlreadyHappened = false;
                    }
                }
                tvCardNumber.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols;
                for (int i = 0; i < s.length(); i++) {
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        Objects.requireNonNull(etCardOwnerInfo.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isFront) {
                    front_animation.setTarget(cardBack);
                    back_animation.setTarget(cardFront);
                    back_animation.start();
                    front_animation.start();
                    isFront = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCardOwnerInfo.setText(charSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Objects.requireNonNull(etExpirationDate.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isFront) {
                    front_animation.setTarget(cardBack);
                    back_animation.setTarget(cardFront);
                    back_animation.start();
                    front_animation.start();
                    isFront = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvExpirationDate.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Objects.requireNonNull(etCVV.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isFront) {
                    front_animation.setTarget(cardFront);
                    back_animation.setTarget(cardBack);
                    front_animation.start();
                    back_animation.start();
                    isFront = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCVV.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
}