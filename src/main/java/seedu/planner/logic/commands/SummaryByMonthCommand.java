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
import seedu.planner.model.summary.SummaryByMonthList;

/** List all the summary of records within a period of time specified */
public class SummaryByMonthCommand extends SummaryCommand {

    public static final String COMMAND_MODE_WORD = "month";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the summary for each month for a period of time."
            + " Parameters: "
            + PREFIX_DATE + "MONTH_START " + "MONTH_END "
            + "Example: " + COMMAND_WORD + " " + COMMAND_MODE_WORD + " "
            + PREFIX_DATE + "sep-2018 " + "oct-2018 ";

    public static final String MESSAGE_SUCCESS = "Listed summary for %d months:";

    private final Date startDate;
    private final Date endDate;
    private final Predicate<Record> predicate;

    public SummaryByMonthCommand(Month startMonth, Month endMonth) {
        startDate = DateUtil.generateFirstOfMonth(startMonth);
        endDate = DateUtil.generateLastOfMonth(endMonth);
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReadOnlyFinancialPlanner financialPlanner = model.getFinancialPlanner();
        SummaryByMonthList summaryList = new SummaryByMonthList(financialPlanner.getRecordList(),
                predicate);
        EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList.getSummaryList()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, summaryList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByMonthCommand // instanceof handles nulls
                && startDate.equals(((SummaryByMonthCommand) other).startDate)
                && endDate.equals(((SummaryByMonthCommand) other).endDate));
    }
}
