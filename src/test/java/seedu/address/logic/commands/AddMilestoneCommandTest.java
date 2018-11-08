package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
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


}
