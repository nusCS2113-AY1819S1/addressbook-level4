package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a title in a note.
 */
public class NoteTitle {

    public static final int TITLE_MAX_CHAR_COUNT = 30;

    public static final String MESSAGE_TITLE_EXCEED_MAX_CHAR_COUNT =
            "Title is too long! Please keep the character count to "
            + Integer.toString(TITLE_MAX_CHAR_COUNT) + " and below.";

    private final String title;

    public NoteTitle(String title) {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        checkArgument(isValidTitle(trimmedTitle), MESSAGE_TITLE_EXCEED_MAX_CHAR_COUNT);
        this.title = (trimmedTitle.isEmpty()) ? "" : trimmedTitle;
    }

    public static boolean isValidTitle(String title) {
        return (title.length() <= TITLE_MAX_CHAR_COUNT);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
