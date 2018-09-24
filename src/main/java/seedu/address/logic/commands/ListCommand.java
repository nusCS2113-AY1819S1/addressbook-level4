package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.record.Date;

/**
 * Lists all records in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final int ARG_COUNT = 2;

    public static final String MESSAGE_USAGE = "Lists all records within a specific time period. "
            + "Parameters: "
            + PREFIX_DATE + "START_DATE END_DATE";

    public static final String MESSAGE_SUCCESS = "Listed all records";

    private final Date startDate;
    private final Date endDate;

    public ListCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
