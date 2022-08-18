package com.nsbtas.nsbtas.utils;

import static com.nsbtas.nsbtas.utils.Utils.changeFragment;

import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;
import com.nsbtas.nsbtas.models.Card;
import com.nsbtas.nsbtas.ui.fragments.ChooseServiceFragment;
import com.nsbtas.nsbtas.ui.fragments.ConfirmAndProceedPaymentFragment;
import com.nsbtas.nsbtas.ui.fragments.CustomerInformationFragment;
import com.nsbtas.nsbtas.ui.fragments.PlaceAmountFragment;
import com.nsbtas.nsbtas.ui.fragments.SelectPaymentMethodFragment;
import com.nsbtas.nsbtas.ui.fragments.TransactionResultFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiStepPaymentFormHelper {
    private static final HashMap<Integer, Fragment> stages = new HashMap<>();
    private static final HashMap<Integer, List<TextInputLayout>> requiredFields = new HashMap<>();
    private static final AtomicInteger currentPage = new AtomicInteger(1);
    public static int cardId;
    private static int transitionId;
    private static Card chosenCard;

    private static boolean isServiceChosen = false;
    private static boolean isAmountPlaced = false;

    public static void setStages() {
        stages.put(1, new ChooseServiceFragment());
        stages.put(2, new CustomerInformationFragment());
        stages.put(3, new SelectPaymentMethodFragment());
        stages.put(4, new PlaceAmountFragment());
        stages.put(5, new ConfirmAndProceedPaymentFragment());
        stages.put(6, new TransactionResultFragment());
    }

    public static void setRequiredFields(int page, List<TextInputLayout> fields) {
        requiredFields.put(page, fields);
    }

    public static boolean checkRequiredFields() {
        boolean error = false;
        if (getCurrentPage() == 1) {
            error = !isServiceChosen;
        } else if (getCurrentPage() == 2) {
            List<TextInputLayout> tmp = requiredFields.get(getCurrentPage());
            for (TextInputLayout field : tmp) {
                if (field.getEditText().getText().toString().isEmpty()) {
                    field.setError("Bu alan boş bırakılamaz");
                    error = true;
                }
            }
            return error;
        } else if (getCurrentPage() == 3) {
            error = chosenCard == null;
        } else if (getCurrentPage() == 4) {
            error = !isAmountPlaced;
        }
        return error;
    }

    public static void nextStage(FragmentManager fragmentManager) {
        if (!checkRequiredFields()) {
            currentPage.getAndIncrement();
            if (currentPage.get() < stages.size() + 1) {
                changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())), fragmentManager);
            }
        }
    }

    public static void previousStage(FragmentManager fragmentManager) {
        currentPage.getAndDecrement();
        if (currentPage.get() > 0) {
            changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())), fragmentManager);
        }
    }

    public static int getTransitionId() {
        return transitionId;
    }

    public static void setTransitionId(int transitionId) {
        MultiStepPaymentFormHelper.transitionId = transitionId;
    }

    public static int getCardId() {
        return cardId;
    }

    public static void setCardId(int cardId) {
        MultiStepPaymentFormHelper.cardId = cardId;
    }

    public static int getCurrentPage() {
        return currentPage.get();
    }

    public static void setCurrentPage(int page) {
        currentPage.set(page);
    }

    public static void reset() {
        isServiceChosen = false;
        isAmountPlaced = false;
        chosenCard = null;
        currentPage.set(1);
    }

    public static void setChosenCard(Card card) {
        chosenCard = card;
    }

    public static Card getChosenCard() {
        return chosenCard;
    }

    public static boolean isIsServiceChosen() {
        return isServiceChosen;
    }

    public static void setIsServiceChosen(boolean isServiceChosen) {
        MultiStepPaymentFormHelper.isServiceChosen = isServiceChosen;
    }

    public static void setIsAmountPlaced(boolean isAmountPlaced) {
        MultiStepPaymentFormHelper.isAmountPlaced = isAmountPlaced;
    }
}
