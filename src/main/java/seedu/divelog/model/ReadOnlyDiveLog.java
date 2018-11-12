package seedu.divelog.model;

import javafx.collections.ObservableList;
import seedu.divelog.model.dive.DiveSession;

/**
 * Unmodifiable view of an divelog book
 */
public interface ReadOnlyDiveLog {

    /**
     * Returns an unmodifiable view of the dive session list.
     * This list will not contain any duplicate dive sessions.
     */
    ObservableList<DiveSession> getDiveList();

}
