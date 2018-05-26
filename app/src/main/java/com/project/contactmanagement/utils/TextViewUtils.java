package com.project.contactmanagement.utils;

public class TextViewUtils {
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
