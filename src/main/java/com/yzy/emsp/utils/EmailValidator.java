package com.yzy.emsp.utils;

import java.util.regex.Pattern;

/**
 * Utility class for validating email addresses.
 */
public class EmailValidator {

    // Regular expression for validating email addresses (RFC 5322 compliant)
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates whether the given email is in a proper format.
     * @param email The email address to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }


}
