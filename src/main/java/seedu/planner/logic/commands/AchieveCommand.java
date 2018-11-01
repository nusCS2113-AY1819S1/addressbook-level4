package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.ExcelUtil.setPathFile;
import static seedu.planner.logic.commands.ExportExcelCommand.exportDataIntoExcelSheetWithGivenRecords;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.SummaryByDateList;
import seedu.planner.ui.SummaryEntry;

/**
 * Achieve the records into Excel file and then deletes all the records exported.
 */
public class AchieveCommand extends Command {
    public static final String COMMAND_WORD = "achieve";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Achieve the records within specific period or all records in the Financial Planner into Excel file.\n"
            + "All the records exported into Excel file will be deleted in the current Financial Planner.\n"
            + "Parameters: START_DATE END_DATE DIRECTORY_PATH,START_DATE should be equal to or smaller than END_DATE.\n"
            + "You can specifically type what you want to conform. Date/period start with d/ "
            + "and Directory path start with dir/.\n"
            + "Example 1: You want to set Directory: "
            + COMMAND_WORD + " dir/" + DirectoryPath.HOME_DIRECTORY_STRING + "\n"
            + "Example 2: You want to set records have only 1 date: " + COMMAND_WORD + " d/31-3-1999\n"
            + "Example 3: You want to set records whose date lies within the period: "
            + COMMAND_WORD + "d/31-3-1999 31-3-2019\n"
            + "Example 4: You want to achieve all the records in the Financial Planner: " + COMMAND_WORD + "\n"
            + "Example 5: You want to achieve records lies within the period and set specific Directory: "
            + COMMAND_WORD + " d/31-3-1999 31-3-2019" + " dir/" + DirectoryPath.HOME_DIRECTORY_STRING + "\n";

    public static final int SINGLE_MODE = 1;
    public static final int DUO_MODE = 2;
    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;

    private static Logger logger = LogsCenter.getLogger(AchieveCommand.class);
    private final Date startDate;
    private final Date endDate;
    private final String directoryPath;
    private final Predicate<Record> predicate;

    public AchieveCommand() {
        this.startDate = null;
        this.endDate = null;
        this.directoryPath = DirectoryPath.HOME_DIRECTORY_STRING;
        this.predicate = PREDICATE_SHOW_ALL_RECORDS;
    }

    public AchieveCommand(String directoryPath) {
        this.startDate = null;
        this.endDate = null;
        this.directoryPath = directoryPath;
        this.predicate = PREDICATE_SHOW_ALL_RECORDS;
    }

    public AchieveCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.directoryPath = DirectoryPath.HOME_DIRECTORY_STRING;
        this.predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    public AchieveCommand(Date startDate, Date endDate, String directoryPath) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.directoryPath = directoryPath;
        this.predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(this);
        model.updateFilteredRecordList(predicate);
        ReadOnlyFinancialPlanner financialPlanner = model.getFinancialPlanner();
        SummaryByDateList summaryList = new SummaryByDateList(financialPlanner.getRecordList(), predicate);
        List<Record> recordList = model.getFilteredRecordList();
        List<SummaryEntry> daySummaryEntryList = summaryList.getSummaryList();
        String nameFile = ExcelUtil.setNameExcelFile(startDate, endDate);
        String message;
        String filePath = setPathFile(nameFile, directoryPath);
        if (exportDataIntoExcelSheetWithGivenRecords(recordList, daySummaryEntryList, filePath)) {
            message = String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY
                    + Messages.MESSAGE_ACHIEVE_SUCCESSFULLY, nameFile, directoryPath);
        } else {
            message = Messages.MESSAGE_ACHIEVE_COMMAND_ERRORS;
        }
        model.deleteListRecord(recordList);
        model.commitFinancialPlanner();
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AchieveCommand // instanceof handles nulls
                && predicate.equals(((AchieveCommand) other).predicate)
                && directoryPath.equals(((AchieveCommand) other).directoryPath)); // state check
    }
}
