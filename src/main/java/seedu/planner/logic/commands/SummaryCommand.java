package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.function.Predicate;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.commons.util.DateUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.Month;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.SummaryByDateList;
import seedu.planner.model.summary.SummaryByMonthList;

/** List all the summary of records within a period of time specified */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the summary for each day for a period of time. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 ";

    public static final String MESSAGE_SUCCESS = "Listed summary for %d days/months:";

    private final Date startDate;
    private final Date endDate;
    private final Predicate<Record> predicate;
    private final boolean byDate;

    public SummaryCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
        byDate = true;
    }

    public SummaryCommand(Month startMonth, Month endMonth) {
        startDate = DateUtil.generateFirstOfMonth(startMonth);
        endDate = DateUtil.generateLastOfMonth(endMonth);
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
        byDate = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReadOnlyFinancialPlanner financialPlanner = model.getFinancialPlanner();
        if (byDate) {
            SummaryByDateList summaryList = new SummaryByDateList(financialPlanner.getRecordList(),
                    predicate);
            EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList.getSummaryList()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, summaryList.size()));
        } else {
            SummaryByMonthList summaryList = new SummaryByMonthList(financialPlanner.getRecordList(),
                    predicate);
            EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList.getSummaryList()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, summaryList.size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryCommand // instanceof handles nulls
                && startDate.equals(((SummaryCommand) other).startDate)
                && endDate.equals(((SummaryCommand) other).endDate));
    }
}
