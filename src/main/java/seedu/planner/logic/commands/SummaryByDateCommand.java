package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.function.Predicate;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.commons.util.DateUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.SummaryByDateList;

//@@author tenvinc
/** List all the summary of records within a period of time specified */
public class SummaryByDateCommand extends SummaryCommand {

    public static final String COMMAND_MODE_WORD = "date";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the summary for each day for a period of time. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + "Example: " + COMMAND_WORD + " " + COMMAND_MODE_WORD + " "
            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 ";

    public static final String MESSAGE_SUCCESS = "Listed summary for %d days";
    public static final String FORMAT_TITLE_SUMMARY = "Summary by date from %s to %s";

    private final Date startDate;
    private final Date endDate;
    private final Predicate<Record> predicate;

    public SummaryByDateCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        SummaryByDateList summaryList = new SummaryByDateList(model.getFilteredRecordList());
        EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList.getSummaryList(),
                summaryList.getTotalExpense(), summaryList.getTotalIncome(), summaryList.getTotal(), TOTAL_LABEL,
                String.format(FORMAT_TITLE_SUMMARY, DateUtil.formatDate(startDate), DateUtil.formatDate(endDate))));
        return new CommandResult(String.format(MESSAGE_SUCCESS, summaryList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByDateCommand // instanceof handles nulls
                && startDate.equals(((SummaryByDateCommand) other).startDate)
                && endDate.equals(((SummaryByDateCommand) other).endDate))
                && predicate.equals(((SummaryByDateCommand) other).predicate);
    }
}
