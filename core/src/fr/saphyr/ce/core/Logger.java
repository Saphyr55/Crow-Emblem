package fr.saphyr.ce.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Logger {

    public abstract void info(String message);

    public abstract void warning(String message);

    public abstract void warning(String message, Exception exception);

    public abstract void debug(String message);

    public abstract void error(String message);

    public abstract void error(String message, Exception exception);

    public static <T extends Logger> T create(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void log(String color, String message) {
        System.out.println(color + currentTime() + " " + message + ANSI.RESET);
    }

    protected void log(String color, String message, Exception exception) {
        System.out.println(color + currentTime() + " " +
                message + " :\n" + exception.getMessage() + ANSI.RESET);
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
        public static final String WHITE = "\u001B[37m";
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
