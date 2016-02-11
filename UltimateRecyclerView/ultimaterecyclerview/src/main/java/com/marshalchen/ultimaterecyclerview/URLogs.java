package com.marshalchen.ultimaterecyclerview;

import android.util.Log;

/**
 * Only for debug
 */
public final class URLogs {
    private static boolean sIsLogEnabled = true;

    private static String sApplicationTag = "Chen";

    private static final String TAG_CONTENT_PRINT = "%s:%s.%s:%d";

    private static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];

    }


    public static void trace() {
        if (sIsLogEnabled) {
            Log.d(sApplicationTag,
                    getContent(getCurrentStackTraceElement()));
        }
    }

    private static String getContent(StackTraceElement trace) {
        return String.format(TAG_CONTENT_PRINT, sApplicationTag,
                trace.getClassName(), trace.getMethodName(),
                trace.getLineNumber());
    }

    private static String getContents(StackTraceElement trace) {
        return String.format("%s:%s:%d", sApplicationTag,
                trace.getMethodName(),
                trace.getLineNumber());
    }

    public static void traceStack() {
        if (sIsLogEnabled) {
            traceStack(sApplicationTag, Log.ERROR);
        }
    }

    public static void traceStack(String tag, int priority) {

        if (sIsLogEnabled) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Log.println(priority, tag, stackTrace[4].toString());
            StringBuilder str = new StringBuilder();
            String prevClass = null;
            for (int i = 5; i < stackTrace.length; i++) {
                String className = stackTrace[i].getFileName();
                int idx = className.indexOf(".java");
                if (idx >= 0) {
                    className = className.substring(0, idx);
                }
                if (prevClass == null || !prevClass.equals(className)) {

                    str.append(className.substring(0, idx));

                }
                prevClass = className;
                str.append(".").append(stackTrace[i].getMethodName())
                        .append(":").append(stackTrace[i].getLineNumber())
                        .append("->");
            }
            Log.println(priority, tag, str.toString());
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg na
     */
    public static void v(String msg) {
        if (sIsLogEnabled) {
            Log.v(sApplicationTag, getContents(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag na
     * @param msg na
     */
    public static void d(String tag, String msg) {
        if (sIsLogEnabled) {
            Log.d(tag, getContent(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg na
     */
    public static void d(String msg) {
        if (sIsLogEnabled) {
            Log.d(sApplicationTag, getContents(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param tag na
     * @param msg na
     */
    public static void i(String tag, String msg) {
        if (sIsLogEnabled) {
            Log.i(tag, getContent(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * @param message na
     * @param args    na
     */
    public static void d(String message, Object... args) {
        if (sIsLogEnabled) {
            d(String.format(message, args));
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param tag na
     * @param msg na
     */
    public static void w(String tag, String msg) {
        if (sIsLogEnabled) {
            Log.w(tag, getContent(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag na
     * @param msg na
     */
    public static void e(String tag, String msg) {
        if (sIsLogEnabled) {
            Log.e(tag, getContent(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg na
     */
    public static void i(String msg) {
        if (sIsLogEnabled) {
            Log.i(sApplicationTag, getContent(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param msg na
     */
    public static void w(String msg) {
        if (sIsLogEnabled) {
            Log.w(sApplicationTag, getContent(getCurrentStackTraceElement()) + ">" + msg);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg na
     */
    public static void e(String msg) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + msg);

        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param exception na
     */
    public static void e(Exception exception) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param exception na
     * @param string    na
     */
    public static void e(Exception exception, String string) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + exception.getMessage() + "\n>" + exception.getStackTrace() + "   " + string);
            exception.printStackTrace();
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param string    na
     * @param exception na
     */
    public static void e(String string, Exception exception) {
        if (sIsLogEnabled) {
            Log.e(sApplicationTag, getContent(getCurrentStackTraceElement()) + "\n>" + exception.getMessage() + "\n>" + exception.getStackTrace() + "   " + string);
            exception.printStackTrace();
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag       na
     * @param message   na
     * @param exception na
     */
    public static void e(String tag, String message, Exception exception) {
        if (sIsLogEnabled) {
            Log.e(tag, getContent(getCurrentStackTraceElement()) + "\n>" + exception.getMessage() + "\n>" + exception.getStackTrace() + "   " + message);
            exception.printStackTrace();
        }
    }


    public static boolean issIsLogEnabled() {
        return sIsLogEnabled;
    }


    /**
     * Set if the Logs print log or not
     *
     * @param sIsLogEnabled na
     */
    public static void setsIsLogEnabled(boolean sIsLogEnabled) {
        URLogs.sIsLogEnabled = sIsLogEnabled;
    }


    public static String getsApplicationTag() {
        return sApplicationTag;
    }

    public static void setsApplicationTag(String sApplicationTag) {
        URLogs.sApplicationTag = sApplicationTag;
    }

    public static String getTagContentPrint() {
        return TAG_CONTENT_PRINT;
    }
}
