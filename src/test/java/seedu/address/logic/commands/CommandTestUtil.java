package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DEADLINE_1ST_JAN = "1/1";
    public static final String VALID_DEADLINE_31ST_MARCH = "31/3";
    public static final String VALID_TITLE_1 = "Complete CS2113 Homework";
    public static final String VALID_TITLE_2 = "Start coding test units";
    public static final String VALID_DESCRIPTION_1 = "Refer to notes";
    public static final String VALID_DESCRIPTION_2 = "Do this before integration tests";
    public static final String VALID_PRIORITY_LEVEL_LOW = "low";
    public static final String VALID_PRIORITY_LEVEL_HIGH = "high";

    public static final String DEADLINE_DESC_1ST_JAN = " " + PREFIX_DEADLINE + VALID_DEADLINE_1ST_JAN;
    public static final String DEADLINE_DESC_31ST_MARCH = " " + PREFIX_DEADLINE + VALID_DEADLINE_31ST_MARCH;
    public static final String TITLE_DESC_1 = " " + PREFIX_TITLE + VALID_TITLE_1;
    public static final String TITLE_DESC_2 = " " + PREFIX_TITLE + VALID_TITLE_2;
    public static final String DESCRIPTION_DESC_1 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_1;
    public static final String DESCRIPTION_DESC_2 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_2;
    public static final String PRIORITY_LEVEL_DESC_LOW = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_LOW;
    public static final String PRIORITY_LEVEL_DESC_HIGH = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_HIGH;

    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "31/2"; // No 31st February in calendar
    public static final String INVALID_PRIORITY_LEVEL_DESC = " " + PREFIX_PRIORITY + "mid"; // not a priority level

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //Mainly for EditCommandTests --> can remove
    //public static final EditCommand.EditPersonDescriptor DESC_AMY;
    //public static final EditCommand.EditPersonDescriptor DESC_BOB;
    /*
    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }
    */

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered task list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }
    /*
    //Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
    //{@code model}'s address book.

    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }


    //Deletes the first task in {@code model}'s filtered list from {@code model}'s address book.

    public static void deleteFirstPerson(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        model.deleteTask(firstTask);
        model.commitTaskBook();
    }
    */

}
