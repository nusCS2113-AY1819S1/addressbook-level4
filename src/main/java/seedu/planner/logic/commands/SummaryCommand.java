package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.summary.Summary;

/** List all the summary of records within a period of time specified */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the summary for each day for a period of time. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 ";

    public static final String MESSAGE_SUCCESS = "Listed summary for %d days:";

    private final Date startDate;
    private final Date endDate;

    public SummaryCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ObservableList<Summary> summaryList = model.getSummaryList(startDate, endDate);
        EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList));
        return new CommandResult(String.format(MESSAGE_SUCCESS, summaryList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryCommand // instanceof handles nulls
                && startDate.equals(((SummaryCommand) other).startDate)
                && endDate.equals(((SummaryCommand) other).endDate));
    }
}
