package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.record.Date;
import seedu.address.model.record.DateIsWithinIntervalPredicate;
import seedu.address.model.record.Record;

/**
 * Lists all records in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final int DUO_ARG_MODE_COUNT = 2;
    public static final int SINGLE_ARG_MODE_COUNT = 1;


    public static final String MESSAGE_USAGE = "Lists all records within a specific time period. "
            + "Parameters: "
            + PREFIX_DATE + "START_DATE END_DATE\n"
            + "END_DATE must be later than or equal to START_DATE.";


    public static final String MESSAGE_SUCCESS = "Listed all records";

    private final Date startDate;
    private final Date endDate;

    private final Predicate<Record> predicate;

    public ListCommand() {
        predicate = PREDICATE_SHOW_ALL_RECORDS;
        startDate = null;
        endDate = null;
    }

    public ListCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate));
    }
}
