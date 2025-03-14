package com.yzy.emsp.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class RFIDCardUtil {

    // Cryptographically secure random number generator
    private static final SecureRandom random = new SecureRandom();

    private static final byte MANUFACTURER_CODE = (byte) 0xE0; // Example: NXP manufacturer code

    /**
     * Generates an 8-byte VisibleId (Unique Identifier).
     *
     * @return A formatted UID string in hexadecimal representation.
     */
    public static String generateVisibleId() {
        byte[] uid = new byte[8];

        uid[0] = MANUFACTURER_CODE; // First byte: Manufacturer code
        random.nextBytes(uid); // Generate random bytes
        uid[0] = MANUFACTURER_CODE; // Ensure the first byte is always the manufacturer code

        // Convert to hexadecimal string
        StringBuilder hexUID = new StringBuilder();
        for (byte b : uid) {
            hexUID.append(String.format("%02X ", b));
        }
        return hexUID.toString().trim();
    }

    /**
     * Generate 16-character hexadecimal RFID UID
     * Security note: Uses secure random generator to prevent predictable values
     *
     * @return 16-character uppercase hexadecimal string
     */
    public static String generateUID() {
        // 8 bytes = 16 hexadecimal characters
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        StringBuilder uid = new StringBuilder();
        // Convert bytes to uppercase hexadecimal
        for (byte b : bytes) {
            uid.append(String.format("%02X", b));
        }
        return uid.toString();
    }


}
