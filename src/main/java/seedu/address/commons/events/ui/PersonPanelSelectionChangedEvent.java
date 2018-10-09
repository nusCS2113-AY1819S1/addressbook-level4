package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.book.Book;

/**
 * Represents a selection change in the Book List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Book newSelection;

    public PersonPanelSelectionChangedEvent(Book newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Book getNewSelection() {
        return newSelection;
    }
}
