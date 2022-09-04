package fr.saphyr.ce.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static fr.saphyr.ce.core.Logger.ANSI.*;

public final class Logger {

    private static final Logger LOGGER = Logger.create();

    public static void info(Object message) { LOGGER.log(WHITE, message); }

    public static void warning(Object message) { LOGGER.log(YELLOW, message); }

    public static void warning(Object message, Exception exception) { LOGGER.log(GREEN, message, exception); }

    public static void debug(Object message) { LOGGER.log(GREEN, message); }

    public static void error(Object message) { LOGGER.log(RED, message); }

    public static void error(Object message, Exception exception) { LOGGER.log(RED, message, exception); }

    private static Logger create() { return new Logger(); }

    private void log(String color, Object message) {
        System.out.println(color + currentTime() + " " + message.toString() + ANSI.RESET);
    }

    private void log(String color, Object message, Exception exception) {
        System.out.println(color + currentTime() + " " +
                message.toString() + " :\n" + exception.getMessage() + ANSI.RESET);
    }

    private String currentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return "["+dtf.format(now)+"]";
    }

    public final static class ANSI {
        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[97m";
        public static final String GRAY_WHITE = "\u001B[37m";
        public static final String BLACK_BACKGROUND = "\u001B[40m";
        public static final String RED_BACKGROUND = "\u001B[41m";
        public static final String GREEN_BACKGROUND = "\u001B[42m";
        public static final String YELLOW_BACKGROUND = "\u001B[43m";
        public static final String BLUE_BACKGROUND = "\u001B[44m";
        public static final String PURPLE_BACKGROUND = "\u001B[45m";
        public static final String CYAN_BACKGROUND = "\u001B[46m";
        public static final String WHITE_BACKGROUND = "\u001B[47m";
    }


}
