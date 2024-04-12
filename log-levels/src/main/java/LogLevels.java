public class LogLevels {

    private static final String logLevelStart = "[";
    private static final String logLevelEnd = "]:";

    public static String message(String logLine) {
        int end = logLine.indexOf(logLevelEnd);

        return logLine.substring(end + logLevelEnd.length()).trim();
    }

    public static String logLevel(String logLine) {
        int start = logLine.indexOf(logLevelStart);
        int end = logLine.indexOf(logLevelEnd);
        return logLine.substring(start + logLevelStart.length(), end).toLowerCase();
    }

    public static String reformat(String logLine) {
        return message(logLine) + " (" + logLevel(logLine) + ")";
    }
}