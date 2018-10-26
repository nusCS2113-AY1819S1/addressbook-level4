package com.t13g2.forum.commons.exceptions;

public class NotLoggedInException extends Exception {
    public NotLoggedInException() {
    }

    public NotLoggedInException(String message) {
        super(message);
    }
}
