package com.chatbot.commonlibrary.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean isEmailValid(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isPasswordStrong(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    public static boolean isPhoneNumberValid(String phone) {
        return phone != null && phone.matches("^(\\+212|0)([5-7])\\d{8}$");
    }

    public static boolean isLanguageCodeValid(String code) {
        if (code == null) return false;
        return code.equalsIgnoreCase("fr") || code.equalsIgnoreCase("ar");
    }

    public static boolean isValidJwt(String token) {
        if (token == null) return false;
        // JWT format: header.payload.signature (base64-encoded parts)
        return Pattern.matches("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$", token);
    }
}
