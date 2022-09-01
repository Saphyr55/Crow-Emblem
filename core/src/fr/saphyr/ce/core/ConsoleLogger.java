package fr.saphyr.ce.core;

import static fr.saphyr.ce.core.Logger.ANSI.*;

public final class ConsoleLogger extends Logger {

    @Override
    public void info(String message) {
        log(WHITE, message);
    }

    @Override
    public void warning(String message) {
        log(YELLOW, message);
    }

    @Override
    public void warning(String message, Exception exception) {
        log(GREEN, message, exception);
    }

    @Override
    public void debug(String message) {
        log(GREEN, message);
    }

    @Override
    public void error(String message) {
        log(RED, message);
    }

    @Override
    public void error(String message, Exception exception) {
        log(RED, message, exception);
    }

}
