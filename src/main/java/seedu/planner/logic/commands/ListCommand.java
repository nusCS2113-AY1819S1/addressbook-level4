package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import java.util.function.Predicate;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;

//@@author tenvinc
/**
 * Lists all records in the financial planner to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final int DUO_ARG_MODE_COUNT = 2;
    public static final int SINGLE_ARG_MODE_COUNT = 1;


    public static final String MESSAGE_USAGE = "Lists all records within a specific time period. "
            + "Parameters: "
            + PREFIX_DATE + "START_DATE END_DATE\n"
            + "END_DATE must be later than or equal to START_DATE.";


    public static final String MESSAGE_SUCCESS_ALL = "Listed all records.";
    public static final String MESSAGE_SUCCESS_DATE_MODE = "Listed %d record(s) from %s to %s.";

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

    public ListCommand(Date date) {
        this.startDate = date;
        this.endDate = date;
        this.predicate = new DateIsWithinIntervalPredicate(date, date);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        if (startDate == null && endDate == null) {
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        } else {
            return new CommandResult(String.format(
                    MESSAGE_SUCCESS_DATE_MODE, model.getFilteredRecordList().size(), startDate, endDate));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate));
    }
}
