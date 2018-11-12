package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.VersionedModelList.MESSAGE_NO_REDOABLE_STATE_EXCEPTION;
import static seedu.address.model.VersionedModelList.MESSAGE_NO_UNDOABLE_STATE_EXCEPTION;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedModelList;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.ScheduleList;

public class ClearCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeRedo_emptyAddressBookScheduleList_failure() throws Exception {
        Model expectedModel = new ModelManager();
        thrown.expect(VersionedModelList.NoRedoableStateException.class);
        thrown.expectMessage(MESSAGE_NO_REDOABLE_STATE_EXCEPTION);
        expectedModel.redoModelList();
    }

    @Test
    public void executeUndo_emptyAddressBookScheduleList_failure() throws Exception {
        Model expectedModel = new ModelManager();
        // undo -> no more states to undo
        thrown.expect(VersionedModelList.NoUndoableStateException.class);
        thrown.expectMessage(MESSAGE_NO_UNDOABLE_STATE_EXCEPTION);
        expectedModel.undoModelList();
    }


    @Test
    public void execute_emptyAddressBookScheduleList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();
        try {
            assertCommandSuccess(new ClearCommand(), model,
                    commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
                 getTypicalRecruitmentList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
                getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());

        expectedModel.resetAddressBookData(new AddressBook());
        expectedModel.commitAddressBook();
        expectedModel.resetScheduleListData(new ScheduleList());
        expectedModel.commitScheduleList();
        expectedModel.resetDataExpenses(new ExpensesList());
        expectedModel.commitExpensesList();
        expectedModel.resetRecruitmentListData(new RecruitmentList());
        expectedModel.commitRecruitmentPostList();

        try {
            assertCommandSuccess(new ClearCommand(), model,
                    commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

}
