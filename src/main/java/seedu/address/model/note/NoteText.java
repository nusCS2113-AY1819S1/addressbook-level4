package seedu.address.model.note;

import static java.util.Objects.requireNonNull;

/**
 * Represents the content of a note.
 */
public class NoteText {

    public static final int NOTE_MAX_CHARACTER_LIMIT = 300;

    private final String noteText;

    public NoteText(String text) {
        requireNonNull(text);
        this.noteText = text;
    }

    public static boolean isValidNoteText(String noteText) {
        return noteText.length() <= NOTE_MAX_CHARACTER_LIMIT;
    }

    @Override
    public String toString() {
        return this.noteText;
    }
}
