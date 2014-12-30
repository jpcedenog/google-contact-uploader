package org.example.google.contact.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * Class containing simple utility functions
 *
 * @author JP Cedeno
 */
public class Utils {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");

    public static void printTimeStamp() {
        System.out.println(formatter.format(new Date()));
    }

    public static void printTimeStamp(String message) {
        System.out.println(formatter.format(new Date()) + " - " + message);
    }

    public static int parseIntWithDefault(String number, int defaultValue) {
        int result = defaultValue;
        if (NumberUtils.isDigits(number)) {
            result = Integer.parseInt(number);
        }
        return result;
    }

    public static int parseInt(String number) {
        return parseIntWithDefault(number, 0);
    }
}
