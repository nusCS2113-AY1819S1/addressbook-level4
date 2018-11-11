package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import org.junit.Before;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UncomplateTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(), getTypicalExpenditureTracker(),
                new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(), getTypicalExpenditureTracker(),
                new UserPrefs());
    }

}
