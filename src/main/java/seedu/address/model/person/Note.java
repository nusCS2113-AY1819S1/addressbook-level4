package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Note {
    public static final String MESSAGE_NOTE_CONSTRAINTS = "The note should be alphanumeric";
    public static final String DESCRIPTION_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String value;

    public Note(String note){
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_NOTE_CONSTRAINTS);
        value = note;
    }

    public static boolean isValidNote (String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}


