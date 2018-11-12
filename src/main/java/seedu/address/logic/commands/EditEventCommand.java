package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.eventContacts.EventContacts;

/**
 * Edits the details of an existing event in the event book.
 */
public class EditEventCommand extends Command {
    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "EVENT NAME] "
            + "[" + PREFIX_START + "EVENT DATE] "
            + "[" + PREFIX_TIME + "EVENT TIME] "
            + "[" + PREFIX_CONTACT + "EVENT CONTACTS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Class outing "
            + PREFIX_TIME + "1000";
    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Event edited successfully";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        model.updateEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitEventBook();
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedEventName = editEventDescriptor.getEventName()
                .orElse(eventToEdit.getEventName());
        Date updatedEventDate = editEventDescriptor.getEventDate()
                .orElse(eventToEdit.getEventDate());
        Time updatedEventTime = editEventDescriptor.getEventTime()
                .orElse(eventToEdit.getEventTime());
        Set<EventContacts> updatedEventContacts =
                editEventDescriptor.getEventContacts().orElse(eventToEdit.getEventContacts());

        return new Event(updatedEventName, updatedEventDate, updatedEventTime, updatedEventContacts);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditEventDescriptor {
        private EventName eventName;
        private Date eventDate;
        private Time eventTime;
        private Set<EventContacts> eventContacts;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventName(toCopy.eventName);
            setEventDate(toCopy.eventDate);
            setEventTime(toCopy.eventTime);
            setEventContacts(toCopy.eventContacts);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventName, eventDate, eventTime, eventContacts);
        }

        public void setEventName(EventName eventName) {
            this.eventName = eventName;
        }

        public Optional<EventName> getEventName() {
            return Optional.ofNullable(eventName);
        }

        public void setEventDate(Date eventDate) {
            this.eventDate = eventDate;
        }

        public Optional<Date> getEventDate() {
            return Optional.ofNullable(eventDate);
        }

        public void setEventTime(Time eventTime) {
            this.eventTime = eventTime;
        }

        public Optional<Time> getEventTime() {
            return Optional.ofNullable(eventTime);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setEventContacts(Set<EventContacts> eventContacts) {
            this.eventContacts = (eventContacts != null) ? new HashSet<>(eventContacts) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<EventContacts>> getEventContacts() {
            return (eventContacts != null) ? Optional.of(Collections.unmodifiableSet(eventContacts)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventName().equals(e.getEventName())
                    && getEventDate().equals(e.getEventDate())
                    && getEventTime().equals(e.getEventTime())
                    && getEventContacts().equals(e.getEventContacts());
        }
    }

}

