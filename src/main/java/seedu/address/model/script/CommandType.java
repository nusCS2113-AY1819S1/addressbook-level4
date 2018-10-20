package seedu.address.model.script;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a CommandType.
 * Guarantees: immutable; is valid as declared in {@link #isValidCommand(String)}
 */
public class CommandType {
    public static final String MESSAGE_MESSAGE_CONSTRAINTS =
            "Command string is not valid";
    public static final String MESSAGE_VALIDATION_REGEX = "^(add|a|addgroup|addgrp|add_testmarks|addt|clear|c|group|"
            + "grp|delete|d|distinto|di|edit|e|edit_test|et|sendmail|sm|exit|ex|find|f|genlist|gl|display|disp|"
            + "help|h|history|his|list|l|listgroup|lg|redo|r|select|s|undo|u)$";
    public final String value;
    /**
     * Constructs a {@code CommandType}.
     *
     * @param commandType A type of command to be executed.
     */
    public CommandType(String commandType) {
        requireNonNull(commandType);
        checkArgument(isValidCommand(commandType), MESSAGE_MESSAGE_CONSTRAINTS);
        value = commandType;
    }

    /**
     * Returns true if a given string is a valid message.
     */
    public static boolean isValidCommand(String test) {
        return test.matches(MESSAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandType // instanceof handles nulls
                && value.equals(((CommandType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
