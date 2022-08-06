package com.nsbtas.nsbtas.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static String getServiceById(int id) {
        String type = "";
        switch (id) {
            case 1:
                type = "1 Aylık Abonelik";
                break;
            case 2:
                type = "12 Aylık Abonelik";
                break;
            case 3:
                type = "3 Yıllık Nlksoft E-İmza";
                break;
        }
        return type;
    }

    public static int FromDpToPx(Resources resources, float dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
        return Math.round(px);
    }
}
