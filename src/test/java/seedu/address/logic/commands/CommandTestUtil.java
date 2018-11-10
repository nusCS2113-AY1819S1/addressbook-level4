package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.UpdateTaskDescriptorBuilder;

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

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    public static final String VALID_EXPENSE_CATEGORY_SHOPPING = "shopping";
    public static final String VALID_EXPENSE_CATEGORY_MRT = "MRT";
    public static final String VALID_EXPENSE_DATE_SHOPPING = "11/11/2011";
    public static final String VALID_EXPENSE_DATE_MRT = "11/11/2011";
    public static final String VALID_EXPENSE_VALUE_SHOPPING = "11.11";
    public static final String VALID_EXPENSE_VALUE_MRT = "11.11";
    public static final String VALID_TAG_TAOBAO = "taobao";

    public static final String EXPENSE_CATEGORY_DESC_SHOPPING =
            " " + PREFIX_EXPENSE_CATEGORY + VALID_EXPENSE_CATEGORY_SHOPPING;
    public static final String EXPENSE_CATEGORY_DESC_MRT =
            " " + PREFIX_EXPENSE_CATEGORY + VALID_EXPENSE_CATEGORY_MRT;
    public static final String EXPENSE_DATE_DESC_SHOPPING =
            " " + PREFIX_EXPENSE_DATE + VALID_EXPENSE_DATE_SHOPPING;
    public static final String EXPENSE_DATE_DESC_MRT =
            " " + PREFIX_EXPENSE_DATE + VALID_EXPENSE_DATE_MRT;
    public static final String EXPENSE_VALUE_DESC_SHOPPING =
            " " + PREFIX_EXPENSE_VALUE + VALID_EXPENSE_VALUE_SHOPPING;
    public static final String EXPENSE_VALUE_DESC_MRT =
            " " + PREFIX_EXPENSE_VALUE + VALID_EXPENSE_VALUE_MRT;
    public static final String TAG_DESC_TAOBAO = " " + PREFIX_TAG + VALID_TAG_TAOBAO;

    public static final String INVALID_EXPENSE_CATEGORY_DESC =
            " " + PREFIX_EXPENSE_CATEGORY + "James&"; // '&' not allowed in expense category
    public static final String INVALID_EXPENSE_DATE_DESC =
            " " + PREFIX_EXPENSE_DATE + "1/1/2019"; // not in DD/MM/YYYY format
    public static final String INVALID_EXPENSE_VALUE_DESC =
            " " + PREFIX_EXPENSE_VALUE; // empty string not allowed for expense value

    public static final String VALID_TASK_NAME_QUIZ = "Quiz Submission";
    public static final String VALID_TASK_NAME_ASSIGN = "Assignment Submission";
    public static final String VALID_BODY_QUIZ = "MA1508E Quiz9";
    public static final String VALID_BODY_ASSIGN = "CG2027 Assign5";
    public static final String VALID_START_DATETIME_QUIZ = "15/10";
    public static final String VALID_START_DATETIME_ASSIGN = "30/10";
    public static final String VALID_END_DATETIME_QUIZ = "21/10_22:00";
    public static final String VALID_END_DATETIME_ASSIGN = "6/11_14:00";
    public static final String VALID_PRIORITY_QUIZ = "MED";
    public static final String VALID_PRIORITY_ASSIGN = "HIGH";
    public static final String VALID_TAG_IVLE = "IVLE";
    public static final String VALID_TAG_HARDCOPY = "hardcopy";

    public static final String TASKNAME_DESC_QUIZ = " " + PREFIX_NAME + VALID_TASK_NAME_QUIZ;
    public static final String TASKNAME_DESC_ASSIGN = " " + PREFIX_NAME + VALID_TASK_NAME_ASSIGN;
    public static final String BODY_DESC_QUIZ = " " + PREFIX_BODY + VALID_BODY_QUIZ;
    public static final String BODY_DESC_ASSIGN = " " + PREFIX_BODY + VALID_BODY_ASSIGN;
    public static final String START_DATETIME_DECS_QUIZ = " " + PREFIX_START + VALID_START_DATETIME_QUIZ;
    public static final String START_DATETIME_DECS_ASSIGN = " " + PREFIX_START + VALID_START_DATETIME_ASSIGN;
    public static final String END_DATETIME_DECS_QUIZ = " " + PREFIX_END + VALID_END_DATETIME_QUIZ;
    public static final String END_DATETIME_DECS_ASSIGN = " " + PREFIX_END + VALID_END_DATETIME_ASSIGN;
    public static final String PRIORITY_DESC_QUIZ = " " + PREFIX_PRIORITY + VALID_PRIORITY_QUIZ;
    public static final String PRIORITY_DESC_ASSIGN = " " + PREFIX_PRIORITY + VALID_PRIORITY_ASSIGN;
    public static final String TAG_DESC_IVLE = " " + PREFIX_TAG + VALID_TAG_IVLE;
    public static final String TAG_DESC_HARDCOPY = " " + PREFIX_TAG + VALID_TAG_HARDCOPY;

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditExpenseCommand.EditExpenseDescriptor DESC_SHOPPING;
    public static final EditExpenseCommand.EditExpenseDescriptor DESC_MRT;

    public static final UpdateTaskCommand.UpdateTaskDescriptor DESC_QUIZ;
    public static final UpdateTaskCommand.UpdateTaskDescriptor DESC_ASSIGN;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_MRT = new EditExpenseDescriptorBuilder().withExpenseCategory(VALID_EXPENSE_CATEGORY_MRT)
                .withExpenseDate(VALID_EXPENSE_DATE_MRT).withExpenseValue(VALID_EXPENSE_VALUE_MRT)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_SHOPPING = new EditExpenseDescriptorBuilder().withExpenseCategory(VALID_EXPENSE_CATEGORY_SHOPPING)
                .withExpenseDate(VALID_EXPENSE_DATE_SHOPPING).withExpenseValue(VALID_EXPENSE_VALUE_SHOPPING)
                .withTags(VALID_TAG_TAOBAO, VALID_TAG_HUSBAND).build();
        DESC_QUIZ = new UpdateTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_QUIZ)
                .withBody(VALID_BODY_QUIZ).withStartDateTime(VALID_START_DATETIME_QUIZ)
                .withEndDateTime(VALID_END_DATETIME_QUIZ).withPriority(VALID_PRIORITY_QUIZ)
                .withTags(VALID_TAG_IVLE).build();
        DESC_ASSIGN = new UpdateTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_ASSIGN)
                .withBody(VALID_BODY_ASSIGN).withStartDateTime(VALID_START_DATETIME_ASSIGN)
                .withEndDateTime(VALID_END_DATETIME_ASSIGN).withPriority(VALID_PRIORITY_ASSIGN)
                .withTags(VALID_TAG_IVLE, VALID_TAG_HARDCOPY).build();
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
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Deletes the first expense in {@code model}'s filtered list from {@code model}'s expense book.
     */
    public static void deleteFirstExpense(Model model) {
        Expense firstExpense = model.getFilteredExpenseList().get(0);
        model.deleteExpense(firstExpense);
        model.commitExpenseBook();
    }


    /**
     * Deletes the first expense in {@code model}'s filtered list from {@code model}'s expense book.
     */
    /*
    public static void deleteFirstExpense(Model model) {
        Expense firstExpense = model.getFilteredExpenseList().get(0);
        model.deleteExpense(firstExpense);
        model.commitExpenseBook();
    }*/
}
