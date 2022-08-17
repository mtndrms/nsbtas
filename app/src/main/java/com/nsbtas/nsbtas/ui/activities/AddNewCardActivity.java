package com.nsbtas.nsbtas.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contentful.java.cma.CMACallback;
import com.contentful.java.cma.model.CMAEntry;
import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.network.CMAClient;
import com.nsbtas.nsbtas.utils.Callback;
import com.nsbtas.nsbtas.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddNewCardActivity extends AppCompatActivity {
    boolean isFront = true;
    boolean isTransactionAlreadyHappened = false;
    AnimatorSet front_animation;
    AnimatorSet back_animation;
    LinearLayout cardFront;
    LinearLayout cardBack;
    Callback callback;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        Utils.setTheme(this);

        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS", Context.MODE_PRIVATE);
        AppCompatButton btnAdd = findViewById(R.id.btnAddCard);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextInputLayout etCardNumber = findViewById(R.id.etCardNumber);
        TextInputLayout etCardOwnerInfo = findViewById(R.id.etCardOwnerInfo);
        TextInputLayout etExpirationDate = findViewById(R.id.etExpirationDate);
        TextInputLayout etCVV = findViewById(R.id.etCVV);
        TextView tvCardNumber = findViewById(R.id.tvCardNumber);
        TextView tvCardOwnerInfo = findViewById(R.id.tvCardOwnerInfo);
        TextView tvExpirationDate = findViewById(R.id.tvExpirationDate);
        TextView tvCVV = findViewById(R.id.tvCVV);
        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
        ImageView ivLogo = findViewById(R.id.ivLogo);

        front_animation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_turn_front_animator);
        back_animation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_turn_back_animator);

        List<TextInputLayout> requiredFields = Arrays.asList(etCardNumber, etCardOwnerInfo, etExpirationDate, etCVV);

        this.callback = this::finish;

        btnBack.setOnClickListener(view -> finish());

        btnAdd.setOnClickListener(view -> {
            final CMAEntry newCard =
                    new CMAEntry()
                            .setId(String.valueOf(UUID.randomUUID()))
                            .setField("cardNumber", "en-US", Objects.requireNonNull(etCardNumber.getEditText()).getText().toString())
                            .setField("cardOwner", "en-US", Objects.requireNonNull(etCardOwnerInfo.getEditText()).getText().toString())
                            .setField("expirationDate", "en-US", Objects.requireNonNull(etExpirationDate.getEditText()).getText().toString())
                            .setField("cvv", "en-US", Objects.requireNonNull(etCVV.getEditText()).getText().toString())
                            .setField("provider", "en-US", etCardNumber.getEditText().getText().toString().startsWith("4") ? "Visa" : "Mastercard");

            CMAClient.getClient().entries().async().create("card", newCard, new CMACallback<CMAEntry>() {
                @Override
                protected void onSuccess(CMAEntry resultCard) {
                    CMAClient.getClient().entries().async().fetchOne(sharedPreferences.getString("userEntryId", "empty"), new CMACallback<CMAEntry>() {
                        @Override
                        protected void onSuccess(CMAEntry result) {
                            List<CMAEntry> cmaCards = result.getField("cards", "en-US");
                            cmaCards.add(resultCard);
                            result.setField("cards", "en-US", cmaCards);
                            CMAClient.getClient().entries().async().update(result, new CMACallback<CMAEntry>() {
                                @Override
                                protected void onSuccess(CMAEntry result) {
                                }

                                @Override
                                protected void onFailure(RuntimeException exception) {
                                    super.onFailure(exception);
                                }
                            });
                        }
                    });
                }

                @Override
                protected void onFailure(RuntimeException exception) {
                    super.onFailure(exception);
                }
            });
            this.callback.callback();
        });

        // Format card number (eg. 4242 4242 4242 4242)
        Objects.requireNonNull(etCardNumber.getEditText()).addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19;
            private static final int TOTAL_DIGITS = 16;
            private static final int DIVIDER_MODULO = 5;
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1;
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TransitionDrawable transition = (TransitionDrawable) cardFront.getBackground();
                if (s.toString().startsWith("4")) {
                    ivLogo.setImageResource(R.drawable.logo_visa);
                    if (!isTransactionAlreadyHappened) {
                        transition.startTransition(500);
                        isTransactionAlreadyHappened = true;
                    }
                } else if (s.toString().startsWith("5")) {
                    ivLogo.setImageResource(R.drawable.logo_mastercard);
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
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCVV.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCardNumber.getEditText().setOnTouchListener((view, motionEvent) -> {
            if (!isFront) {
                turnCardFront();
            }
            return false;
        });

        etCardOwnerInfo.getEditText().setOnTouchListener((view, motionEvent) -> {
            if (!isFront) {
                turnCardFront();
            }
            return false;
        });

        etExpirationDate.getEditText().setOnTouchListener((view, motionEvent) -> {
            if (!isFront) {
                turnCardFront();
            }
            return false;
        });

        etCVV.getEditText().setOnTouchListener((view, motionEvent) -> {
            if (isFront) {
                turnCardBack();
            }
            return false;
        });
    }

    private void turnCardBack() {
        front_animation.setTarget(cardFront);
        back_animation.setTarget(cardBack);
        front_animation.start();
        back_animation.start();
        isFront = false;
    }

    private void turnCardFront() {
        front_animation.setTarget(cardBack);
        back_animation.setTarget(cardFront);
        front_animation.start();
        back_animation.start();
        isFront = true;
    }
}