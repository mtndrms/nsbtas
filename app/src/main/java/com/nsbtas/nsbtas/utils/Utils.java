package com.nsbtas.nsbtas.utils;

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
}
