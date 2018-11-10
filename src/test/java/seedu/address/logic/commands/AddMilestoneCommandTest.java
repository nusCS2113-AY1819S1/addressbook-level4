package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalMilestones.FIRST_MILESTONE;
import static seedu.address.testutil.TypicalMilestones.SECOND_MILESTONE;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.Task;
import seedu.address.testutil.MilestoneBuilder;

//@@author JeremyInElysium
public class AddMilestoneCommandTest {

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_milestoneAcceptedByModel_addSuccessful() {

        Milestone validMilestone = new MilestoneBuilder().build();
        Task editedTask = model.getFilteredTaskList().get(0).addMilestone(validMilestone);
        AddMilestoneCommand addMilestoneCommand = new AddMilestoneCommand(INDEX_FIRST_TASK, validMilestone);

        String expectedMessage = String.format(AddMilestoneCommand.MESSAGE_SUCCESS, validMilestone.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(addMilestoneCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {

        AddMilestoneCommand addFirstMilestoneCommand = new AddMilestoneCommand(INDEX_FIRST_TASK, FIRST_MILESTONE);
        AddMilestoneCommand addSecondMilestoneCommand = new AddMilestoneCommand(INDEX_FIRST_TASK, SECOND_MILESTONE);

        //same object -> returns true
        assertTrue(addFirstMilestoneCommand.equals(addFirstMilestoneCommand));

        //same values -> return true
        AddMilestoneCommand addFirstMilestoneCommandCopy = new AddMilestoneCommand(INDEX_FIRST_TASK, FIRST_MILESTONE);
        assertTrue(addFirstMilestoneCommand.equals(addFirstMilestoneCommandCopy));

        //different types -> return false
        assertFalse(addFirstMilestoneCommand.equals(1));

        //null -> return false
        assertFalse(addFirstMilestoneCommand.equals(null));

        //different milestone -> return false
        assertFalse(addFirstMilestoneCommand.equals(addSecondMilestoneCommand));
    }

}
