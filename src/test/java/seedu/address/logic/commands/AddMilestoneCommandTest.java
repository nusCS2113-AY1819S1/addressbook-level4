package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalMilestones.FIRST_MILESTONE;
import static seedu.address.testutil.TypicalMilestones.SECOND_MILESTONE;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.Task;
import seedu.address.testutil.MilestoneBuilder;

//@@author JeremyInElysium
public class AddMilestoneCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
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
    public void execute_milestoneWithInvalidIndex_throwsCommandException() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();

        AddMilestoneCommand addMilestoneCommand = new AddMilestoneCommand(INDEX_FIFTH_TASK, validMilestone);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMilestoneCommand.MESSAGE_TASK_NOT_FOUND);
        addMilestoneCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_milestoneWithSameRank_throwsCommandException() throws Exception {
        Milestone firstMilestone = new MilestoneBuilder(FIRST_MILESTONE).build();
        Task editedTask = model.getFilteredTaskList().get(0).addMilestone(firstMilestone);
        model.updateTask(model.getFilteredTaskList().get(0), editedTask);

        AddMilestoneCommand addMilestoneCommand = new AddMilestoneCommand(INDEX_FIRST_TASK, firstMilestone);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMilestoneCommand.MESSAGE_DUPLICATE_RANK);
        addMilestoneCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_milestoneWithSameMilestoneDescription_throwsCommandException() throws Exception {
        Milestone firstMilestone = new MilestoneBuilder(FIRST_MILESTONE).build();
        Milestone secondMilestone = new MilestoneBuilder(FIRST_MILESTONE).withRank("2").build();
        Task editedTask = model.getFilteredTaskList().get(0).addMilestone(firstMilestone);
        model.updateTask(model.getFilteredTaskList().get(0), editedTask);

        AddMilestoneCommand addMilestoneCommand = new AddMilestoneCommand(INDEX_FIRST_TASK, secondMilestone);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMilestoneCommand.MESSAGE_DUPLICATE_MILESTONEDESCRIPTION);
        addMilestoneCommand.execute(model, commandHistory);
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
