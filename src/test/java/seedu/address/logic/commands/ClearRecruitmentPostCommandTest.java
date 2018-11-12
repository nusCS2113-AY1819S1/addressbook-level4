package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.testutil.Assert;

public class ClearRecruitmentPostCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyRecruitmentList_throwsCommandException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitRecruitmentPostList();

        ClearRecruitmentPostCommand clearRecruitmentPostCommand = new ClearRecruitmentPostCommand();
        Assert.assertThrows(CommandException.class, () -> clearRecruitmentPostCommand.execute(model, commandHistory));
        assertCommandFailure(new ClearRecruitmentPostCommand(), model,
                commandHistory, ClearRecruitmentPostCommand.MESSAGE_FAILURE_CLEARED);
    }

    @Test
    public void execute_nonEmptyRecruitmentPostList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
                getTypicalRecruitmentList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
                getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());

        expectedModel.resetRecruitmentListData(new RecruitmentList());
        expectedModel.commitRecruitmentPostList();

        try {
            assertCommandSuccess(new ClearRecruitmentPostCommand(), model,
                    commandHistory, ClearRecruitmentPostCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

}
