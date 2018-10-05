package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.util.CollectionUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;

/**
 * Edits the details of an existing record in the expense book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the record identified "
            + "by the index number used in the displayed record list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_MONEYFLOW + "MONEYFLOW] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "10-10-2017 "
            + PREFIX_MONEYFLOW + "+4.50";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Edited Record: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the financial planner.";

    private final Index index;
    private final EditRecordDescriptor editRecordDescriptor;

    /**
     * @param index of the record in the filtered record list to edit
     * @param editRecordDescriptor details to edit the record with
     */
    public EditCommand(Index index, EditRecordDescriptor editRecordDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecordDescriptor);

        this.index = index;
        this.editRecordDescriptor = new EditRecordDescriptor(editRecordDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = createEditedRecord(recordToEdit, editRecordDescriptor);

        if (!recordToEdit.isSameRecord(editedRecord) && model.hasRecord(editedRecord)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.updateRecord(recordToEdit, editedRecord);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        model.commitFinancialPlanner();
        return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS, editedRecord));
    }

    /**
     * Creates and returns a {@code Record} with the details of {@code recordToEdit}
     * edited with {@code editRecordDescriptor}.
     */
    private static Record createEditedRecord(Record recordToEdit, EditRecordDescriptor editRecordDescriptor) {
        assert recordToEdit != null;

        Name updatedName = editRecordDescriptor.getName().orElse(recordToEdit.getName());
        Date updatedDate = editRecordDescriptor.getDate().orElse(recordToEdit.getDate());
        MoneyFlow updatedMoneyFlow = editRecordDescriptor.getMoneyFlow().orElse(recordToEdit.getMoneyFlow());
        Set<Tag> updatedTags = editRecordDescriptor.getTags().orElse(recordToEdit.getTags());

        return new Record(updatedName, updatedDate, updatedMoneyFlow, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editRecordDescriptor.equals(e.editRecordDescriptor);
    }

    /**
     * Stores the details to edit the record with. Each non-empty field value will replace the
     * corresponding field value of the record.
     */
    public static class EditRecordDescriptor {
        private Name name;
        private Date date;
        private MoneyFlow moneyFlow;
        private Set<Tag> tags;

        public EditRecordDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setMoneyFlow(toCopy.moneyFlow);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, moneyFlow, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setMoneyFlow(MoneyFlow moneyFlow) {
            this.moneyFlow = moneyFlow;
        }

        public Optional<MoneyFlow> getMoneyFlow() {
            return Optional.ofNullable(moneyFlow);
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
            if (!(other instanceof EditRecordDescriptor)) {
                return false;
            }

            // state check
            EditRecordDescriptor e = (EditRecordDescriptor) other;

            return getName().equals(e.getName())
                    && getDate().equals(e.getDate())
                    && getMoneyFlow().equals(e.getMoneyFlow())
                    && getTags().equals(e.getTags());
        }
    }
}
