package com.forbidden.island.utils;

/**
 * LogUtil utility class
 * Used for unified log output, currently implemented as console output (System.out).
 */
public class LogUtil {

    /**
     * Output log message to console.
     *
     * @param log log content to output, type is String
     */
    public static void console(String log) {
        System.out.println(log);
    }
}
