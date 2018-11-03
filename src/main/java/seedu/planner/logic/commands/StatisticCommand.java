package seedu.planner.logic.commands;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.CategoryStatisticsList;
//@@author tenvinc
/**
 * Shows the statistics of the current summary
 */
public class StatisticCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Provides a pie chart breakdown of data "
            + "within a certain time period according to the categories."
            + " A category is defined as any set of tags assigned to a record.\n"
            + "Parameters: "
            + PREFIX_DATE + "START_DATE " + "END_DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DATE + "1-1-2018 " + "12-12-2018";

    public static final String MESSAGE_SUCCESS = "Stats generated for data from %s to %s!";

    private final Predicate<Record> predicate;
    private final Date startDate;
    private final Date endDate;

    public StatisticCommand(Date startDate, Date endDate) {
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
        predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ObservableList<Record> recordList = model.getFinancialPlanner().getRecordList();
        List<Record> filteredList = filterRecordListByPredicate(recordList, predicate);
        CategoryStatisticsList categoryStats = new CategoryStatisticsList(filteredList);
        EventsCenter.getInstance().post(new ShowPieChartStatsEvent(categoryStats.getReadOnlyStatsList()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, startDate, endDate));
    }

    public List<Record> filterRecordListByPredicate(List<Record> recordList, Predicate<Record> predicate) {
        return recordList.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticCommand // instanceof handles nulls
                && predicate.equals(((StatisticCommand) other).predicate));
    }
}
