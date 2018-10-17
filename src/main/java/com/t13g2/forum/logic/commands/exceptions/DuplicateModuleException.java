package com.t13g2.forum.logic.commands.exceptions;

import com.t13g2.forum.logic.commands.Command;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class DuplicateModuleException extends Exception {
    public DuplicateModuleException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public DuplicateModuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
