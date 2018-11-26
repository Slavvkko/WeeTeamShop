package com.hordiienko.weeteamshop.utils;

import android.os.Build;
import android.text.Html;

import com.hordiienko.weeteamshop.Constants;

import java.util.Locale;

public class Utils {
    public static String removeHtmlTags(String src) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(src, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(src).toString();
        }
    }

    public static String formatImageUrl(int productId, int imageId) {
        return String.format(Locale.getDefault(), Constants.API_IMAGE_URL, productId, imageId);
    }

    public static String formatPrice(float price) {
        return String.format(Locale.GERMANY, "%.2f", price);
    }
}
