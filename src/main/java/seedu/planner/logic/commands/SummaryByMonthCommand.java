package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.commons.util.DateUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.SummaryByMonthList;
import seedu.planner.model.summary.SummaryList;

//@@author tenvinc
/** List all the summary of records within a period of time specified */
public class SummaryByMonthCommand extends SummaryCommand {

    public static final String COMMAND_MODE_WORD = "month";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_MODE_WORD
            + ": Lists the summary for each month for a period of time."
            + " Parameters: "
            + PREFIX_DATE + "START_MONTH " + "END_MONTH "
            + "Example: " + COMMAND_WORD + " " + COMMAND_MODE_WORD + " "
            + PREFIX_DATE + "sep-2018 " + "oct-2018 ";

    public static final String MESSAGE_SUCCESS = "Listed summary for %d month(s)";
    public static final String FORMAT_TITLE_SUMMARY = "Summary by month from %s to %s";

    private static Logger logger = LogsCenter.getLogger(SummaryByMonthCommand.class);

    private final Date startDate;
    private final Date endDate;
    private final Predicate<Record> predicate;

    public SummaryByMonthCommand(Month startMonth, Month endMonth) {
        startDate = DateUtil.generateFirstOfMonth(startMonth);
        endDate = DateUtil.generateLastOfMonth(endMonth);
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SummaryList summaryList;
        try {
            model.updateFilteredRecordList(predicate);
            summaryList = new SummaryByMonthList(model.getFilteredRecordList());
            logger.info("Created SummaryByMonthList: " + summaryList.size() + " summaries");
            String tabTitle = String.format(FORMAT_TITLE_SUMMARY, DateUtil.formatDate(startDate),
                    DateUtil.formatDate(endDate));
            EventsCenter.getInstance().post(new ShowSummaryTableEvent(summaryList, TOTAL_LABEL,
                    tabTitle));
        } catch (Exception e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }
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
