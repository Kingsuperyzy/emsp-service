package com.yzy.emsp.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class RFIDCardUtil {

    // Prefix for visible ID
    private static final String PREFIX = "EV";
    // Atomic counter for sequential numbering (could be replaced with database persistence)
    private static final AtomicInteger counter = new AtomicInteger(1);
    // Cryptographically secure random number generator
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generate visible ID with format: EV-YYYYMM-000001
     * Format breakdown:
     * - Prefix: EV
     * - Date: Current year and month (yyyyMM)
     * - Sequence: 6-digit incremental number
     *
     * @return Formatted visible ID string
     */
    public static String generateVisibleId() {
        // Get current year and month in "yyyyMM" format
        String datePart = new SimpleDateFormat("yyyyMM").format(new Date());
        // Get and increment sequence number atomically
        int uniqueNumber = counter.getAndIncrement();
        // Format: Prefix-Date-Sequence (6-digit zero-padded)
        return String.format("%s-%s-%06d", PREFIX, datePart, uniqueNumber);
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
