package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author junweiljw
/**
 * Represents a Reminder's meeting agenda in JitHub.
 * Guarantees: immutable; is valid as declared in {@link #isValidAgenda(String)}
 */
public class Agenda {

    public static final String MESSAGE_AGENDA_CONSTRAINTS =
            "Agenda can take any values, and it should not be blank";

    /*
     * The first character of the content must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String AGENDA_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Agenda}.
     *
     * @param agenda A valid agenda.
     */
    public Agenda(String agenda) {
        requireNonNull(agenda);
        checkArgument(isValidAgenda(agenda), MESSAGE_AGENDA_CONSTRAINTS);
        value = agenda;
    }

    /**
     * Returns true if a given string is a valid content.
     */
    public static boolean isValidAgenda(String test) {
        return test.matches(AGENDA_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Agenda // instanceof handles nulls
                && value.equals(((Agenda) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
