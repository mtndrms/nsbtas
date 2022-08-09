package com.nsbtas.nsbtas.utils;

import static com.nsbtas.nsbtas.utils.Utils.changeFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nsbtas.nsbtas.ui.fragments.ChooseServiceFragment;
import com.nsbtas.nsbtas.ui.fragments.ConfirmAndProceedPaymentFragment;
import com.nsbtas.nsbtas.ui.fragments.CustomerInformationFragment;
import com.nsbtas.nsbtas.ui.fragments.PlaceAmountFragment;
import com.nsbtas.nsbtas.ui.fragments.SelectPaymentMethodFragment;
import com.nsbtas.nsbtas.ui.fragments.TransactionResultFragment;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiStepPaymentFormHelper {
    private static final HashMap<Integer, Fragment> stages = new HashMap<>();
    private static final HashMap<Integer, String> titles = new HashMap<>();
    private static final AtomicInteger currentPage = new AtomicInteger(1);
    public static int cardId;
    private static int transitionId;

    public static void setStages() {
        stages.put(1, new ChooseServiceFragment());
        stages.put(2, new CustomerInformationFragment());
        stages.put(3, new SelectPaymentMethodFragment());
        stages.put(4, new PlaceAmountFragment());
        stages.put(5, new ConfirmAndProceedPaymentFragment());
        stages.put(6, new TransactionResultFragment());

        titles.put(1, "Servis seçin");
        titles.put(2, "Bilgilerinizi girin");
        titles.put(3, "Kart seçin");
        titles.put(4, "Ödenecek tutarı girin");
        titles.put(5, "Girdiğiniz bilgileri kontrol edin\nve ödemeyi tamamlayın");
        titles.put(6, "Sonuc");
    }

    public static String getCurrentStageTitle() {
        return titles.get(currentPage.get());
    }

    public static void nextStage(FragmentManager fragmentManager) {
        if (currentPage.get() < 6) {
            currentPage.getAndIncrement();
            changeFragment(Objects.requireNonNull(stages.get(currentPage.get())).getTag(), Objects.requireNonNull(stages.get(currentPage.get())), fragmentManager);
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
        currentPage.set(1);
    }
}
