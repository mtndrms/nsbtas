package com.nsbtas.nsbtas.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.ui.fragments.ChooseServiceFragment;
import com.nsbtas.nsbtas.ui.fragments.ConfirmAndProceedPaymentFragment;
import com.nsbtas.nsbtas.ui.fragments.CustomerInformationFragment;
import com.nsbtas.nsbtas.ui.fragments.PlaceAmountFragment;
import com.nsbtas.nsbtas.ui.fragments.SelectPaymentMethodFragment;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiStepPaymentFormHelper {
    private static final HashMap<Integer, Fragment> stages = new HashMap<>();
    private static final AtomicInteger currentPage = new AtomicInteger(1);
    public static int cardId;

    public static void setStages() {
        stages.put(1, new ChooseServiceFragment());
        stages.put(2, new CustomerInformationFragment());
        stages.put(3, new SelectPaymentMethodFragment());
        stages.put(4, new PlaceAmountFragment());
        stages.put(5, new ConfirmAndProceedPaymentFragment());
    }

    public static void nextStage(FragmentManager fragmentManager) {
        if (currentPage.get() < 5) {
            currentPage.getAndIncrement();
            changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())), fragmentManager);
        }
    }

    public static void previousStage(FragmentManager fragmentManager) {
        if (currentPage.get() > 1) {
            currentPage.getAndDecrement();
            changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())), fragmentManager);
        }
    }

    private static void changeFragment(String tag, Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
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
}
