package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoAddressBook();
        model.undoModelList();
        model.undoAddressBook();
        model.undoModelList();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.undoModelList();
        expectedModel.undoAddressBook();
        expectedModel.undoModelList();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoAddressBook();
        try {
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        // single redoable state in model
        expectedModel.redoAddressBook();
        try {
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
