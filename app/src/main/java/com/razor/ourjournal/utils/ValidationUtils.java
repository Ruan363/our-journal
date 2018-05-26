package com.razor.ourjournal.utils;


import android.text.TextUtils;

public class ValidationUtils {

    public static boolean isValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return target.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        }
    }
}
