package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Note {
    public static final String MESSAGE_NOTE_CONSTRAINTS = "Notes or Descriptions should only contain alphanumeric characters and spaces";
    public static final String NOTE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String NOTE_NOT_PROVIDED = "No notes for this contact";
    public final String value;

    public Note(){
        value = NOTE_NOT_PROVIDED;
    }

    public Note(String note){
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_NOTE_CONSTRAINTS);
        value = note;
    }

    public static boolean isValidNote (String test) {
        return test.matches(NOTE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}


