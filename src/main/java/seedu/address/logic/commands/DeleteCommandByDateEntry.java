package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Date;
import seedu.address.model.record.Record;

/**
 * Delete the records whose date is required.
 */
public class DeleteCommandByDateEntry extends Command {
    public static final String COMMAND_WORD = "delete_date";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete the all records identified by the date number used in the displayed record list.\n"
            + "PARAMERERS: DATE (Must follow the format dd-mm-yyyy.\n"
            + "Example: "
            + COMMAND_WORD
            + "31-03-1999";

    public static final String MESSAGE_DELETE_RECORD_SUCCESS = "Deleted all records whose date is %1$s";

    private final Date targetDate;

    public DeleteCommandByDateEntry(Date targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();
        boolean targetRecordExist = false;
        for (Record targetRecord : lastShownList) {
            Date date = targetRecord.getDate();
            if (date == targetDate) {
                model.deleteRecord(targetRecord);
                model.commitAddressBook();
                targetRecordExist = true;
            }
        }
        if (!targetRecordExist) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_DATE);
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_RECORD_SUCCESS, targetDate));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommandByDateEntry // instanceof handles nulls
                && targetDate.equals(((DeleteCommandByDateEntry) other).targetDate)); // state check
    }
}
