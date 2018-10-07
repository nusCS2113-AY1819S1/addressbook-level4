package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Address;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.Email;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.tag.Tag;

/**
 * Replies a comment in the comment section of the event
 */
public class ReplyCommentCommand extends Command {

    public static final String COMMAND_WORD = "replyComment";

    public static final String MESSAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_REPLY_COMMENT = "Comment replied for Event %1$s at Line %2$s";
    public static final String MESSAGE_NOT_REPLY_COMMENT = "At least one field to edit must be provided.";

    private final Index index;
    private final EditCommentDescriptor editCommentDescriptor;
    private int line = 0;
    private String comment = null;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editCommentDescriptor details to edit the event with
     */
    public ReplyCommentCommand(Index index, int line, String comment, Name name) {
        requireNonNull(index);
        requireNonNull(line);
        requireNonNull(comment);
        requireNonNull(name);

        this.index = index;
        this.line = line;
        this.comment = comment;
        this.editCommentDescriptor = new EditCommentDescriptor();
        editCommentDescriptor.setName(name);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> filteredEventList = model.getFilteredEventList();

        if (index.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        Event eventToEdit = filteredEventList.get(index.getZeroBased());
        Event editedEvent = createEditedComment(eventToEdit, editCommentDescriptor);

        model.updateEvent(eventToEdit, editedEvent);
        model.commitEventManager();

        return new CommandResult(String.format(MESSAGE_REPLY_COMMENT, index.getOneBased(), editedEvent.getName()));
    }


    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Event createEditedComment(Event eventToEdit, EditCommentDescriptor editCommentDescriptor) {
        assert eventToEdit != null;

        Name updatedName = editCommentDescriptor.getName().orElse(eventToEdit.getName());
        Phone updatedPhone = editCommentDescriptor.getPhone().orElse(eventToEdit.getPhone());
        Email updatedEmail = editCommentDescriptor.getEmail().orElse(eventToEdit.getEmail());
        Address updatedAddress = editCommentDescriptor.getAddress().orElse(eventToEdit.getAddress());
        Attendance updatedAttendance = editCommentDescriptor.getAttendance().orElse(eventToEdit.getAttendance());
        Set<Tag> updatedTags = editCommentDescriptor.getTags().orElse(eventToEdit.getTags());

        return new Event(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedAttendance, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReplyCommentCommand)) {
            return false;
        }

        // state check
        ReplyCommentCommand e = (ReplyCommentCommand) other;
        return index.equals(e.index)
                && editCommentDescriptor.equals(e.editCommentDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditCommentDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Attendance attendance;
        private Set<Tag> tags;

        public EditCommentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCommentDescriptor(EditCommentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setAttendance(toCopy.attendance);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }

        public Optional<Attendance> getAttendance() {
            return Optional.ofNullable(attendance);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommentDescriptor)) {
                return false;
            }

            // state check
            EditCommentDescriptor e = (EditCommentDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
