package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_MARITAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_OCCUPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_WEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
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
import seedu.address.testutil.AddInfoPersonDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Person
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_COY = "Coy Doh";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_COY = "33333333";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_COY = "coy@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_COY = "Block 456, Coy Street 5";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_EMPTY_INPUT = "";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_COY = " " + PREFIX_NAME + VALID_NAME_COY;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_COY = " " + PREFIX_PHONE + VALID_PHONE_COY;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_COY = " " + PREFIX_EMAIL + VALID_EMAIL_COY;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_COY = " " + PREFIX_ADDRESS + VALID_ADDRESS_COY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String VALID_NRIC_AMY = "S9799909J";
    public static final String VALID_NRIC_BOB = "S9880095F";
    public static final String VALID_DATE_OF_BIRTH_AMY = "10-02-1986";
    public static final String VALID_DATE_OF_BIRTH_BOB = "01-05-2005";
    public static final String VALID_HEIGHT_AMY = "168";
    public static final String VALID_WEIGHT_AMY = "60.5";
    public static final String VALID_GENDER_AMY = "F";
    public static final String VALID_BLOODTYPE_AMY = "O+";
    public static final String VALID_OCCUPATION_AMY = "Nurse";
    public static final String VALID_MARITAL_STATUS_AMY = "S";

    public static final String NRIC_DESC_AMY = " " + PREFIX_ADD_INFO_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_ADD_INFO_NRIC + VALID_NRIC_BOB;
    public static final String DOB_DESC_AMY = " " + PREFIX_ADD_INFO_DOB + VALID_DATE_OF_BIRTH_AMY;
    public static final String HEIGHT_DESC_AMY = " " + PREFIX_ADD_INFO_HEIGHT + VALID_HEIGHT_AMY;
    public static final String WEIGHT_DESC_AMY = " " + PREFIX_ADD_INFO_WEIGHT + VALID_WEIGHT_AMY;
    public static final String GENDER_DESC_AMY = " " + PREFIX_ADD_INFO_GENDER + VALID_GENDER_AMY;
    public static final String BLOODTYPE_DESC_AMY = " " + PREFIX_ADD_INFO_BLOODTYPE + VALID_BLOODTYPE_AMY;
    public static final String OCCUPATION_DESC_AMY = " " + PREFIX_ADD_INFO_OCCUPATION + VALID_OCCUPATION_AMY;
    public static final String MARITAL_STATUS_DESC_AMY = " " + PREFIX_ADD_INFO_MARITAL + VALID_MARITAL_STATUS_AMY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    // MedHistory
    public static final String VALID_HISTORY_DATE = "10-10-2010";
    public static final String VALID_HISTORY_DATE2 = "20-10-2010";
    public static final String VALID_HISTORY_OLD_DATE = "10-10-1990";
    public static final String VALID_HISTORY_ALLERGY = "Pollen";
    public static final String VALID_HISTORY_ALLERGY2 = "Alcohol";
    public static final String VALID_HISTORY_COUNTRY = "USA";
    public static final String VALID_HISTORY_COUNTRY2 = "Russia";
    public static final String VALID_HISTORY_DISCHARGE_STATUS = "d";
    public static final String VALID_HISTORY_DISCHARGE_STATUS2 = "a";
    public static final String INVALID_HISTORY_DATE = "10-10-2020";

    //Medical Report
    public static final String VALID_TITLE1 = "Asthma";
    public static final String VALID_TITLE2 = "Depression";
    public static final String VALID_TITLE3 = "Flu";
    public static final String VALID_DATE1 = "01-01-2018";
    public static final String VALID_DATE2 = "02-02-2018";
    public static final String VALID_DATE3 = "03-03-2018";
    public static final String VALID_INFO1 = "Prescribed XXX medicine, next appointment on 02-02-2018.";
    public static final String VALID_INFO2 = "Prescribed XXX medicine, next appointment on 03-03-2018.";
    public static final String VALID_INFO3 = "Prescribed XXX medicine, next appointment on 04-04-2018.";

    public static final String VALID_TITLE_WPREFIX = " " + PREFIX_TITLE + VALID_TITLE1;
    public static final String VALID_DATE_WPREFIX = " " + PREFIX_DATE + VALID_DATE1;
    public static final String VALID_INFO_WPREFIX = " " + PREFIX_INFO + VALID_INFO1;

    // empty string not allowed for title
    public static final String INVALID_TITLE_WPREFIX = " " + PREFIX_TITLE + " ";
    // date not in dd-MM-yy format
    public static final String INVALID_DATE_WPREFIX = " " + PREFIX_DATE + "01/01/2018";
    // empty string not allowed for information
    public static final String INVALID_INFO_WPREFIX = " " + PREFIX_INFO + " ";

    // Appt
    public static final String VALID_START_APPT1 = "01-01-2018 14:00";
    public static final String VALID_START_APPT2 = "02-02-2018 15:00";
    public static final String VALID_START_APPT3 = "03-03-2018 16:00";
    public static final String VALID_START_APPT4 = "03-03-2018 15:30";
    public static final String VALID_END_APPT1 = "01-01-2018 15:00";
    public static final String VALID_END_APPT2 = "02-02-2018 16:00";
    public static final String VALID_END_APPT3 = "03-03-2018 17:00";
    public static final String VALID_END_APPT4 = "03-03-2018 16:30";
    public static final String VALID_VENUE_APPT1 = "Consultation Room 1";
    public static final String VALID_VENUE_APPT2 = "Consultation Room 2";
    public static final String VALID_VENUE_APPT3 = "Consultation Room 3";
    public static final String VALID_INFO_APPT1 = "Diabetes Checkup";
    public static final String VALID_INFO_APPT2 = "Asthma Checkup";
    public static final String VALID_INFO_APPT3 = "Eye Checkup";
    public static final String VALID_DRNAME_APPT1 = "Dr Tan";
    public static final String VALID_DRNAME_APPT2 = "Dr Lim";
    public static final String VALID_DRNAME_APPT3 = "Dr Chan";

    public static final String VALID_START_APPT_WPREFIX = " " + PREFIX_APPT_START + VALID_START_APPT1;
    public static final String VALID_END_APPT_WPREFIX = " " + PREFIX_APPT_END + VALID_END_APPT1;
    public static final String VALID_VENUE_APPT_WPREFIX = " " + PREFIX_APPT_VENUE + VALID_VENUE_APPT1;
    public static final String VALID_INFO_APPT_WPREFIX = " " + PREFIX_APPT_INFO + VALID_INFO_APPT1;
    public static final String VALID_DRNAME_APPT_WPREFIX = " " + PREFIX_APPT_DRNAME + VALID_DRNAME_APPT1;

    // date not in dd-MM-yy format
    public static final String INVALID_START_APPT_WPREFIX = " " + PREFIX_APPT_START + "01/01/2018 14:00";
    // missing time
    public static final String INVALID_END_APPT_WPREFIX = " " + PREFIX_APPT_END + "02-02-2018";
    // empty string not allowed for appt venues
    public static final String INVALID_VENUE_APPT_WPREFIX = " " + PREFIX_APPT_VENUE + " ";
    // empty string not allowed for appt info
    public static final String INVALID_INFO_APPT_WPREFIX = " " + PREFIX_APPT_INFO + " ";
    // '!' not allowed for appt dr name
    public static final String INVALID_DRNAME_APPT_WPREFIX = " " + PREFIX_APPT_DRNAME + "Dr Tan!";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final AddInfoCommand.AddInfoPersonDescriptor ADDINFO_DESC_AMY;
    public static final AddInfoCommand.AddInfoPersonDescriptor ADDINFO_DESC_BOB;

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

    static {
        ADDINFO_DESC_AMY = new AddInfoPersonDescriptorBuilder().withNric(VALID_NRIC_AMY)
                .withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).build();
        ADDINFO_DESC_BOB = new AddInfoPersonDescriptorBuilder().withNric(VALID_NRIC_BOB)
                .withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).build();
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
