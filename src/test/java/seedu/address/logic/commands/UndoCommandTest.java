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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoAddressBook();
        try {
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        // single undoable state in model
        expectedModel.undoAddressBook();
        try {
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
