package com.yzy.emsp.utils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Utility class for generating and validating EMAID (e-Mobility Account Identifier)
 * based on ISO 15118 and Hubject Plug and Charge standards.
 */
public class EMAIDUtil {
    
    // Standardized EMAID pattern (No separators, uppercase, 14 or 15 characters)
    private static final Pattern STANDARDIZED_PATTERN = Pattern.compile("^[A-Z]{2}[\\dA-Z]{3}[\\dA-Z]{9}[\\dA-Z]?$");

    // Optional separator format pattern (Lowercase, allows "-", optional check digit)
    private static final Pattern OPTIONAL_PATTERN = Pattern.compile("^[a-z]{2}(-?)[\\da-z]{3}\\1[\\da-z]{9}(\\1[\\da-z])?$");

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String COUNTRY_CODES = "DEFRGBNLITUSSECH"; // Example country codes
    private static final String PROVIDER_IDS = "ABCXYZLMNOPQ"; // Example provider IDs
    private static final Random RANDOM = new Random();

    /**
     * Generates a random EMAID in standardized format.
     * @param includeCheckDigit Whether to include an optional check digit.
     * @return Generated EMAID string.
     */
    public static String generateEMAID(boolean includeCheckDigit) {
        String country = getRandomCountryCode();
        String provider = getRandomProviderID();
        String instance = getRandomInstanceID();

        String emaid = country + provider + instance;
        if (includeCheckDigit) {
            emaid += calculateCheckDigit(emaid);
        }
        return emaid;
    }

    /**
     * Validates if the given EMAID matches the standardized format or optional format.
     * @param emaid The EMAID to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean isValidEMAID(String emaid) {
        return STANDARDIZED_PATTERN.matcher(emaid).matches() || OPTIONAL_PATTERN.matcher(emaid).matches();
    }

    /**
     * Normalizes an EMAID by removing hyphens and converting to uppercase.
     * @param emaid The EMAID to normalize.
     * @return Normalized EMAID.
     */
    public static String normalizeEMAID(String emaid) {
        if (emaid == null) {
            return null;
        }
        return emaid.replaceAll("-", "").toUpperCase();
    }

    /**
     * Calculates a simple check digit for EMAID.
     * Uses a basic mod-36 checksum based on ASCII values.
     * @param emaid The EMAID (without check digit).
     * @return Calculated check digit (single character).
     */
    private static char calculateCheckDigit(String emaid) {
        int sum = 0;
        for (char c : emaid.toCharArray()) {
            sum += (c % 36); // Modulo 36 ensures digit or uppercase letter
        }
        return CHARACTERS.charAt(sum % CHARACTERS.length());
    }

    /**
     * Generates a random 2-letter country code.
     * @return Random country code.
     */
    private static String getRandomCountryCode() {
        int index = RANDOM.nextInt(COUNTRY_CODES.length() / 2) * 2;
        return COUNTRY_CODES.substring(index, index + 2);
    }

    /**
     * Generates a random 3-character provider ID.
     * @return Random provider ID.
     */
    private static String getRandomProviderID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(PROVIDER_IDS.charAt(RANDOM.nextInt(PROVIDER_IDS.length())));
        }
        return sb.toString();
    }

    /**
     * Generates a random 9-character instance ID (alphanumeric).
     * @return Random instance ID.
     */
    private static String getRandomInstanceID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Example Usage
        String emaid1 = generateEMAID(false); // 14-character EMAID
        String emaid2 = generateEMAID(true);  // 15-character EMAID (with check digit)
        String emaid3 = "de-abc-123456789-x"; // Example input

        System.out.println("Generated EMAID (14 chars): " + emaid1);
        System.out.println("Generated EMAID (15 chars): " + emaid2);
        System.out.println("Is '" + emaid1 + "' valid? " + isValidEMAID(emaid1));
        System.out.println("Is '" + emaid3 + "' valid? " + isValidEMAID(emaid3));
        System.out.println("Normalized EMAID: " + normalizeEMAID(emaid3));
    }
}
