package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.expenditureinfo.ExpenditureNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;
import seedu.address.testutil.EditExpenditureDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_UNUSED = "unused";
    public static final String VALID_TASK_NAME_ASSIGNMENT = "Assignment 3";
    public static final String VALID_TASK_NAME_TUTORIAL = "Tutorial 5";
    public static final String VALID_TASK_MODULE_ASSIGNMENT = "CS2102";
    public static final String VALID_TASK_MODULE_TUTORIAL = "CG2028";
    public static final String VALID_TASK_DATE_ASSIGNMENT = "19-10";
    public static final String VALID_TASK_DATE_TUTORIAL = "04-08";
    public static final String VALID_TASK_PRIORITY_ASSIGNMENT = "1";
    public static final String VALID_TASK_PRIORITY_TUTORIAL = "2";
    public static final String VALID_EXPENDITURE_DESCRIPTION_CLOTHES = "Clothes";
    public static final String VALID_EXPENDITURE_DESCRIPTION_IPHONE = "iPhoneXS Max";
    public static final String VALID_EXPENDITURE_CATEGORY_CLOTHES = "Clothing";
    public static final String VALID_EXPENDITURE_CATEGORY_IPHONE = "Electronics";
    public static final String VALID_EXPENDITURE_MONEY_CLOTHES = "110";
    public static final String VALID_EXPENDITURE_MONEY_IPHONE = "1599";
    public static final String VALID_EXPENDITURE_DATE_CLOTHES = "01-01-2018";
    public static final String VALID_EXPENDITURE_DATE_IPHONE = "11-07-2018";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TASK_NAME_DESC_ASSIGNMENT = " " + PREFIX_TASK + VALID_TASK_NAME_ASSIGNMENT;
    public static final String TASK_NAME_DESC_TUTORIAL = " " + PREFIX_TASK + VALID_TASK_NAME_TUTORIAL;
    public static final String TASK_MODULE_DESC_ASSIGNMENT = " " + PREFIX_MODULE + VALID_TASK_MODULE_ASSIGNMENT;
    public static final String TASK_MODULE_DESC_TUTORIAL = " " + PREFIX_MODULE + VALID_TASK_MODULE_TUTORIAL;
    public static final String TASK_DATE_DESC_ASSIGNMENT = " " + PREFIX_DATE + VALID_TASK_DATE_ASSIGNMENT;
    public static final String TASK_DATE_DESC_TUTORIAL = " " + PREFIX_DATE + VALID_TASK_DATE_TUTORIAL;
    public static final String TASK_PRIORITY_DESC_ASSIGNMENT = " " + PREFIX_PRIORITY + VALID_TASK_PRIORITY_ASSIGNMENT;
    public static final String TASK_PRIORITY_DESC_TUTORIAL = " " + PREFIX_PRIORITY + VALID_TASK_PRIORITY_TUTORIAL;
    public static final String EXPENDITURE_DESCRIPTION_CLOTHES =
            " " + PREFIX_NAME + VALID_EXPENDITURE_DESCRIPTION_CLOTHES;
    public static final String EXPENDITURE_DESCRIPTION_IPHONE =
            " " + PREFIX_NAME + VALID_EXPENDITURE_DESCRIPTION_IPHONE;
    public static final String EXPENDITURE_CATEGORY_CLOTHES = " " + PREFIX_PHONE + VALID_EXPENDITURE_CATEGORY_CLOTHES;
    public static final String EXPENDITURE_CATEGORY_IPHONE = " " + PREFIX_PHONE + VALID_EXPENDITURE_CATEGORY_IPHONE;
    public static final String EXPENDITURE_MONEY_CLOTHES = " " + PREFIX_EMAIL + VALID_EXPENDITURE_MONEY_CLOTHES;
    public static final String EXPENDITURE_MONEY_IPHONE = " " + PREFIX_EMAIL + VALID_EXPENDITURE_MONEY_IPHONE;
    public static final String EXPENDITURE_DATE_CLOTHES = " " + PREFIX_ADDRESS + VALID_EXPENDITURE_DATE_CLOTHES;
    public static final String EXPENDITURE_DATE_IPHONE = " " + PREFIX_ADDRESS + VALID_EXPENDITURE_DATE_IPHONE;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TASK_NAME_DESC = " " + PREFIX_TASK + "Essay*"; // '*' not allowed in names
    public static final String INVALID_TASK_MODULE_DESC = " " + PREFIX_MODULE + "CS23345"; // invalid module code
    public static final String INVALID_TASK_DATE_DESC = " " + PREFIX_DATE + "0912"; // missing '-' symbol
    public static final String INVALID_TASK_PRIORITY_DESC = " " + PREFIX_TASK + "4"; // only 1, 2, or 3 allowed
    public static final String INVALID_EXPENDITURE_DESCRIPTION_DESC =
            " " + PREFIX_DESCRIPTION + "chicken*"; // '*' not allowed in descriptions
    public static final String INVALID_EXPENDITURE_CATEGORY_DESC =
            " " + PREFIX_CATEGORY + "breakfast"; // invalid category
    public static final String INVALID_EXPENDITURE_MONEY_DESC = " " + PREFIX_MONEY + "91*"; //  '*' not allowed in money
    public static final String INVALID_EXPENDITURE_DATE_DESC = " " + PREFIX_DATE + "01012018"; //  missing '-' symbol

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditTaskCommand.EditTaskDescriptor DESC_ASSIGNMENT;
    public static final EditTaskCommand.EditTaskDescriptor DESC_TUTORIAL;

    public static final EditExpenditureCommand.EditExpenditureDescriptor DESC_IPHONE;
    public static final EditExpenditureCommand.EditExpenditureDescriptor DESC_CLOTHES;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_ASSIGNMENT = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_ASSIGNMENT)
                .withDate(VALID_TASK_DATE_ASSIGNMENT).withModule(VALID_TASK_MODULE_ASSIGNMENT)
                .withPriority(VALID_TASK_PRIORITY_ASSIGNMENT).build();
        DESC_TUTORIAL = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_ASSIGNMENT)
                .withDate(VALID_TASK_DATE_TUTORIAL).withModule(VALID_TASK_MODULE_TUTORIAL)
                .withPriority(VALID_TASK_PRIORITY_TUTORIAL).build();

        DESC_IPHONE = new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_IPHONE)
                .withDate(VALID_EXPENDITURE_DATE_IPHONE).withCategory(VALID_EXPENDITURE_CATEGORY_IPHONE)
                .withMoney(VALID_EXPENDITURE_MONEY_IPHONE).build();
        DESC_CLOTHES = new EditExpenditureDescriptorBuilder().withDescription(VALID_EXPENDITURE_DESCRIPTION_CLOTHES)
                .withDate(VALID_EXPENDITURE_DATE_CLOTHES).withCategory(VALID_EXPENDITURE_CATEGORY_CLOTHES)
                .withMoney(VALID_EXPENDITURE_MONEY_CLOTHES).build();
    }

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
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s to-do list.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new TaskNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s expenditure tracker.
     */
    public static void showExpenditureAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenditureList().size());

        Expenditure expenditure = model.getFilteredExpenditureList().get(targetIndex.getZeroBased());
        final String[] splitName = expenditure.getDescription().descriptionName.split("\\s+");
        model.updateFilteredExpenditureList(new ExpenditureNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExpenditureList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s to-do list.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        model.deleteTask(firstTask);
        model.commitTodoList();
    }
}
