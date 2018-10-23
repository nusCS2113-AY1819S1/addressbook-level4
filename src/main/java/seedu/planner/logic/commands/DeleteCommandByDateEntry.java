package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;

/**
 * Delete the records whose date is required.
 */
public class DeleteCommandByDateEntry extends Command {
    public static final String COMMAND_WORD = "delete_date";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the all records identified by the date number used in the displayed record list.\n"
            + "PARAMERERS: DATE (Must follow the format dd-mm-yyyy).\n"
            + "Example: "
            + COMMAND_WORD
            + " 31-03-1999";

    public static final String MESSAGE_DELETE_RECORDS_SUCCESS = "Deleted all records whose date is %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommandByDateEntry.class);

    private final Date targetDate;

    public DeleteCommandByDateEntry(Date targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Boolean targetRecordExist = Boolean.FALSE;
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();
        for (int i = lastShownList.size() - 1; i >= 0; i--) {
            Record targetRecord = lastShownList.get(i);
            logger.info(String.format(
                    "---------------------------------------------The date required is: %1$s, the date shown is %2$s\n",
                    targetDate.getValue(), targetRecord.getDate().getValue()));
            if (targetRecord.isSameDateRecord(targetDate)) {
                model.deleteRecord(targetRecord);
                targetRecordExist = Boolean.TRUE;
            }
        }
        if (!targetRecordExist) {
            logger.info("The record does not exist.\n");
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_RECORD_DISPLAYED_DATE);
        } else {
            model.commitFinancialPlanner();
            return new CommandResult(String.format(MESSAGE_DELETE_RECORDS_SUCCESS, targetDate.value));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommandByDateEntry // instanceof handles nulls
                && targetDate.equals(((DeleteCommandByDateEntry) other).targetDate)); // state check
    }
}
