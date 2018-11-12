package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGENDA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

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

    //@@author linnnruoo
    public static final String VALID_CONTENT_TASK1 = "Eat my own dog food.";
    public static final String VALID_CONTENT_TASK2 = "Do my own unit tests.";
    public static final String VALID_TITLE_TASK1 = "To eat or not to eat";
    public static final String VALID_TITLE_TASK2 = "To do or not to do";

    //@@author junweiljw
    public static final String VALID_REMINDER1_TITLE = "CS2113T Milestone 1 Meeting";
    public static final String VALID_REMINDER1_DATE = "08022018";
    public static final String VALID_REMINDER1_TIME = "2359";
    public static final String VALID_REMINDER1_AGENDA = "To decide on what features to assign to members";
    public static final String VALID_REMINDER2_TITLE = "CS2101 OP2 Meeting";
    public static final String VALID_REMINDER2_DATE = "05122018";
    public static final String VALID_REMINDER2_TIME = "2300";
    public static final String VALID_REMINDER2_AGENDA = "To decide on what features to assign to members";

    //@@author driedmelon
    public static final String VALID_DATE_EVENT1 = "09092018";
    public static final String VALID_DATE_EVENT2 = "01012018";
    public static final String VALID_START_TIME_EVENT1 = "1230";
    public static final String VALID_START_TIME_EVENT2 = "0900";
    public static final String VALID_END_TIME_EVENT1 = "1400";
    public static final String VALID_END_TIME_EVENT2 = "1100";
    public static final String VALID_EVENT_NAME_EVENT1 = "CS1231 Exam";
    public static final String VALID_EVENT_NAME_EVENT2 = "Meeting for work";

    //@@author driedmelon
    public static final String DATE_DESC_EVENT1 = " " + PREFIX_DATE + VALID_DATE_EVENT1;
    public static final String DATE_DESC_EVENT2 = " " + PREFIX_DATE + VALID_DATE_EVENT2;
    public static final String START_TIME_DESC_EVENT1 = " " + PREFIX_START_TIME + VALID_START_TIME_EVENT1;
    public static final String START_TIME_DESC_EVENT2 = " " + PREFIX_START_TIME + VALID_START_TIME_EVENT2;
    public static final String END_TIME_DESC_EVENT1 = " " + PREFIX_END_TIME + VALID_END_TIME_EVENT1;
    public static final String END_TIME_DESC_EVENT2 = " " + PREFIX_END_TIME + VALID_END_TIME_EVENT2;
    public static final String EVENT_NAME_DESC_EVENT1 = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_EVENT1;
    public static final String EVENT_NAME_DESC_EVENT2 = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_EVENT2;

    //@@author driedmelon
    public static final String VALID_DATE_MATCH1 = "09092018";
    public static final String VALID_START_TIME_MATCH1 = "1000";
    public static final String VALID_END_TIME_MATCH1 = "1600";
    public static final String VALID_MATCH_INDEX1 = "1";
    public static final String VALID_MATCH_INDEX2 = "2";

    //@@author driedmelon
    public static final String DATE_DESC_MATCH1 = " " + PREFIX_DATE + VALID_DATE_MATCH1;
    public static final String START_TIME_DESC_MATCH1 = " " + PREFIX_START_TIME + VALID_START_TIME_MATCH1;
    public static final String END_TIME_DESC_MATCH1 = " " + PREFIX_END_TIME + VALID_END_TIME_MATCH1;
    public static final String INDEX_DESC_MATCH1 = " " + PREFIX_INDEX + VALID_MATCH_INDEX1;
    public static final String INDEX_DESC_MATCH2 = " " + PREFIX_INDEX + VALID_MATCH_INDEX2;

    //@@author driedmelon
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "50502018";
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_START_TIME + "1280";
    public static final String INVALID_LATE_START_TIME_DESC = " " + PREFIX_START_TIME + "1200";
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_END_TIME + "2520";
    public static final String INVALID_EARLY_END_TIME_DESC = " " + PREFIX_END_TIME + "0100";
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + "BOB@!#@";

    //@@author
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

    //@@author linnnruoo
    public static final String CONTENT_DESC_TASK1 = " " + PREFIX_CONTENT + VALID_CONTENT_TASK1;
    public static final String CONTENT_DESC_TASK2 = " " + PREFIX_CONTENT + VALID_CONTENT_TASK2;
    public static final String TITLE_DESC_TASK1 = " " + PREFIX_TITLE + VALID_TITLE_TASK1;
    public static final String TITLE_DESC_TASK2 = " " + PREFIX_TITLE + VALID_TITLE_TASK2;

    //@@author junweiljw
    public static final String TITLE_DESC_REMINDER1 = " " + PREFIX_TITLE + VALID_REMINDER1_TITLE;
    public static final String DATE_DESC_REMINDER1 = " " + PREFIX_DATE + VALID_REMINDER1_DATE;
    public static final String TIME_DESC_REMINDER1 = " " + PREFIX_START_TIME + VALID_REMINDER1_TIME;
    public static final String AGENDA_DESC_REMINDER1 = " " + PREFIX_AGENDA + VALID_REMINDER1_AGENDA;
    public static final String TITLE_DESC_REMINDER2 = " " + PREFIX_TITLE + VALID_REMINDER2_TITLE;
    public static final String DATE_DESC_REMINDER2 = " " + PREFIX_DATE + VALID_REMINDER2_DATE;
    public static final String TIME_DESC_REMINDER2 = " " + PREFIX_START_TIME + VALID_REMINDER2_TIME;
    public static final String AGENDA_DESC_REMINDER2 = " " + PREFIX_AGENDA + VALID_REMINDER2_AGENDA;

    //@@author
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    //@@author linnnruoo
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty string not allowed for content
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE; // empty string not allowed for titles

    //@@author junweiljw
    public static final String INVALID_TIME_DESC = " " + PREFIX_START_TIME + "2359a"; // 'a' not allowed in times
    public static final String INVALID_AGENDA_DESC = " " + PREFIX_AGENDA; // empty string not allowed for agenda

    //@@author
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
}
