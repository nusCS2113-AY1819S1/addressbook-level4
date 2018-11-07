package seedu.planner.logic.commands;

import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Assert;
import org.junit.Test;

import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;

//@author nguyenngoclinhchi
public class ImportExcelCommandTest {
    private static final String directoryPath1 = DirectoryPath.WORKING_DIRECTORY_STRING;
    private static final String directoryPath2 = DirectoryPath.HOME_DIRECTORY_STRING;
    private static final String nameFile = "Financial_Planner_ALL";
    private static final String directoryPathName1 = ExcelUtil.setPathFile(nameFile, directoryPath1);
    private static final String directoryPathName2 = ExcelUtil.setPathFile(nameFile, directoryPath2);

    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() {

        ImportExcelCommand importExcelCommand1 = new ImportExcelCommand(directoryPathName1);
        ImportExcelCommand importExcelCommand2 = new ImportExcelCommand(directoryPathName2);

        Assert.assertTrue(importExcelCommand1.equals(importExcelCommand1));
        Assert.assertTrue(importExcelCommand2.equals(importExcelCommand2));

        ImportExcelCommand importExcelCommand1Copy = new ImportExcelCommand(directoryPathName1);
        ImportExcelCommand importExcelCommand2Copy = new ImportExcelCommand(directoryPathName2);

        Assert.assertTrue(importExcelCommand1.equals(importExcelCommand1Copy));
        Assert.assertTrue(importExcelCommand2.equals(importExcelCommand2Copy));

        Assert.assertFalse(importExcelCommand1.equals(importExcelCommand2));
    }
}
