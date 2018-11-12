package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BONUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFBIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSES_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_EXPENSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MISCELLANEOUS_EXPENSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAVEL_EXPENSES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelTypes;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.PostContainsKeywordsPredicate;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.ModAllSalaryDescriptorBuilder;
import seedu.address.testutil.ModSalaryDescriptorBuilder;
import seedu.address.testutil.expenses.EditExpensesDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_EMPLOYEEID_AMY = "000010";
    public static final String VALID_EMPLOYEEID_BOB = "000011";
    public static final String VALID_EMPLOYEEID_CARL = "000003";
    public static final String VALID_EMPLOYEEID_DAISY = "999999";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DATEOFBIRTH_AMY = "21/03/1970";
    public static final String VALID_DATEOFBIRTH_BOB = "26/10/1993";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_DEPARTMENT_AMY = "Human Resource";
    public static final String VALID_DEPARTMENT_BOB = "Human Resource";
    public static final String VALID_POSITION_AMY = "Director";
    public static final String VALID_POSITION_BOB = "Intern";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_SALARY_AMY = "8000.00";
    public static final String VALID_SALARY_BOB = "1000.00";
    public static final String VALID_BONUS_AMY = "16000.00";
    public static final String VALID_PARSER_BONUS_AMY = "12";
    public static final String VALID_BONUS_BOB = "2000.00";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String INVALID_BONUS_BOB = "25";

    //schedule test fields
    public static final String VALID_DATE_AMY = "01/01/2099";
    public static final String VALID_DATE_BOB = "01/01/2099";
    public static final String VALID_YEAR_BOB = "2099";
    public static final String INVALID_PAST_DATE_BOB = "01/01/2000";
    public static final String VALID_DATE_CARL = "01/02/2099";
    public static final String VALID_DATE_DONNIE = "01/04/2099";
    public static final String VALID_TYPE_AMY = "WORK";
    public static final String VALID_TYPE_BOB = "WORK";
    public static final String VALID_TYPE_CARL = "LEAVE";
    public static final String TYPE_SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE_TYPE + VALID_TYPE_BOB;
    public static final String DATE_SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE_DATE + VALID_DATE_BOB;
    public static final String YEAR_SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE_YEAR + VALID_YEAR_BOB;
    public static final String DATE_INVALID_PAST_SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE_DATE + INVALID_PAST_DATE_BOB;
    public static final String DATE_SCHEDULE_DESC_CARL = " " + PREFIX_SCHEDULE_DATE + VALID_DATE_CARL;
    public static final String DATE_SCHEDULE_DESC_DONNIE = " " + PREFIX_SCHEDULE_DATE + VALID_DATE_DONNIE;
    public static final String INVALID_SCHEDULE_TYPE_DESC = " " + PREFIX_SCHEDULE_TYPE
            + "HALF"; // type should only be WORK or LEAVE
    public static final String INVALID_SCHEDULE_YEAR_DESC = " " + PREFIX_SCHEDULE_YEAR
            + "1500"; // year must be within 2000 to 2099
    public static final String INVALID_SCHEDULE_DATE_DESC = " " + PREFIX_SCHEDULE_DATE
            + "02-02-2099"; // must be in the format of DD/MM/YYYY
    public static final String INVALID_SCHEDULE_DATE_PAST_DESC = " " + PREFIX_SCHEDULE_DATE
            + "1/1/2000"; //cannot schedule for date in the past

    //expenses test fields
    public static final String VALID_EXPENSESAMOUNT_AMY = "1035";
    public static final String VALID_EXPENSESAMOUNT_BOB = "1368";
    public static final String VALID_EXPENSESAMOUNT_CARL = "1701";
    public static final String VALID_EXPENSESAMOUNT_EMPTY = "0";
    public static final String VALID_TRAVELEXPENSES_AMY = "234";
    public static final String VALID_TRAVELEXPENSES_BOB = "345";
    public static final String VALID_TRAVELEXPENSES_CARL = "456";
    public static final String VALID_TRAVELEXPENSES_EMPTY = "0";
    public static final String VALID_MEDICALEXPENSES_AMY = "345";
    public static final String VALID_MEDICALEXPENSES_BOB = "456";
    public static final String VALID_MEDICALEXPENSES_CARL = "567";
    public static final String VALID_MEDICALEXPENSES_EMPTY = "0";
    public static final String VALID_MISCELLANEOUSEXPENSES_AMY = "456";
    public static final String VALID_MISCELLANEOUSEXPENSES_BOB = "567";
    public static final String VALID_MISCELLANEOUSEXPENSES_CARL = "678";
    public static final String VALID_MISCELLANEOUSEXPENSES_EMPTY = "0";

    public static final String EXPENSESAMOUNT_DESC_AMY = " " + PREFIX_EXPENSES_AMOUNT + VALID_EXPENSESAMOUNT_AMY;
    public static final String EXPENSESAMOUNT_DESC_BOB = " " + PREFIX_EXPENSES_AMOUNT + VALID_EXPENSESAMOUNT_BOB;
    public static final String EXPENSESAMOUNT_DESC_EMPTY = " " + PREFIX_EXPENSES_AMOUNT + VALID_EXPENSESAMOUNT_EMPTY;
    public static final String MEDICALEXPENSES_DESC_AMY = " " + PREFIX_MEDICAL_EXPENSES + VALID_MEDICALEXPENSES_AMY;
    public static final String MEDICALEXPENSES_DESC_BOB = " " + PREFIX_MEDICAL_EXPENSES + VALID_MEDICALEXPENSES_BOB;
    public static final String MEDICALEXPENSES_DESC_EMPTY = " " + PREFIX_MEDICAL_EXPENSES + VALID_MEDICALEXPENSES_EMPTY;
    public static final String MISCELLANEOUSEXPENSES_DESC_AMY = " " + PREFIX_MISCELLANEOUS_EXPENSES
            + VALID_MISCELLANEOUSEXPENSES_AMY;
    public static final String MISCELLANEOUSEXPENSES_DESC_BOB = " " + PREFIX_MISCELLANEOUS_EXPENSES
            + VALID_MISCELLANEOUSEXPENSES_BOB;
    public static final String MISCELLANEOUSEXPENSES_DESC_EMPTY = " " + PREFIX_MISCELLANEOUS_EXPENSES
            + VALID_MISCELLANEOUSEXPENSES_EMPTY;
    public static final String TRAVELEXPENSES_DESC_AMY = " " + PREFIX_TRAVEL_EXPENSES + VALID_TRAVELEXPENSES_AMY;
    public static final String TRAVELEXPENSES_DESC_BOB = " " + PREFIX_TRAVEL_EXPENSES + VALID_TRAVELEXPENSES_BOB;
    public static final String TRAVELEXPENSES_DESC_EMPTY = " " + PREFIX_TRAVEL_EXPENSES + VALID_TRAVELEXPENSES_EMPTY;

    public static final String INVALID_EXPENSESAMOUNT_DESC = " " + PREFIX_EXPENSES_AMOUNT + "123z.12";
    // 'z' not allowed in expensesAmounnt
    public static final String INVALID_TRAVELEXPENSES_DESC = " " + PREFIX_TRAVEL_EXPENSES + "123z.12";
    // 'z' not allowed in travelExpenses
    public static final String INVALID_MEDICALEXPENSES_DESC = " " + PREFIX_MEDICAL_EXPENSES + "123z.12";
    // 'z' not allowed in medicalExpenses
    public static final String INVALID_MISCELLANEOUSEXPENSES_DESC = " " + PREFIX_MISCELLANEOUS_EXPENSES + "123z.12";
    // 'z' not allowed in miscellaneousExpenses

    public static final String EMPLOYEEID_DESC_AMY = " " + PREFIX_EMPLOYEEID + VALID_EMPLOYEEID_AMY;
    public static final String EMPLOYEEID_DESC_BOB = " " + PREFIX_EMPLOYEEID + VALID_EMPLOYEEID_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DATEOFBIRTH_DESC_AMY = " " + PREFIX_DATEOFBIRTH + VALID_DATEOFBIRTH_AMY;
    public static final String DATEOFBIRTH_DESC_BOB = " " + PREFIX_DATEOFBIRTH + VALID_DATEOFBIRTH_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DEPARTMENT_DESC_AMY = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_AMY;
    public static final String DEPARTMENT_DESC_BOB = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_BOB;
    public static final String POSITION_DESC_AMY = " " + PREFIX_POSITION + VALID_POSITION_AMY;
    public static final String POSITION_DESC_BOB = " " + PREFIX_POSITION + VALID_POSITION_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String SALARY_DESC_AMY = " " + PREFIX_SALARY + VALID_SALARY_AMY;
    public static final String SALARY_DESC_BOB = " " + PREFIX_SALARY + VALID_SALARY_BOB;
    public static final String BONUS_DESC_AMY = " " + PREFIX_BONUS + VALID_BONUS_AMY;
    public static final String BONUS_PARSER_DESC_AMY = " " + PREFIX_BONUS + VALID_PARSER_BONUS_AMY;
    public static final String BONUS_DESC_BOB = " " + PREFIX_BONUS + VALID_BONUS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_EMPLOYEEID_DESC = " " + PREFIX_EMPLOYEEID
            + "1111111"; // employeeId should only contain 6 digits
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATEOFBIRTH_DESC = " " + PREFIX_DATEOFBIRTH
            + "11/11/111a"; // 'a' not allowed in date of birth
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DEPARTMENT_DESC = " " + PREFIX_DEPARTMENT
            + "12345"; // numbers not allowed in department
    public static final String INVALID_POSITION_DESC = " " + PREFIX_POSITION
            + "@@@@@"; // special characters not allowed in position
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_SALARY_DESC = " " + PREFIX_SALARY + "a123.22"; // 'a' not allowed in salary
    public static final String INVALID_BONUS_DESC = " " + PREFIX_BONUS + "a25"; // 'a' not allowed in bonus
    public static final String INVALID_BONUS_OVER_DESC = " " + PREFIX_BONUS
            + "25"; // bonus input not allow to go above 24
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final AddExpensesCommand.EditExpensesDescriptor CLAIM_AMY;
    public static final AddExpensesCommand.EditExpensesDescriptor CLAIM_BOB;
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final ModifyPayCommand.ModSalaryDescriptor PAY_AMY;
    public static final ModifyPayCommand.ModSalaryDescriptor PAY_BOB;
    public static final ModifyAllPayCommand.ModSalaryDescriptor PAY_ALL;

    //recruitment test field
    public static final String VALID_POST_RE3 = "Finance Manager";
    public static final String VALID_WORK_EXP_RE3 = "8";
    public static final String VALID_JOB_DESCRIPTION_RE3 = "To ensure the financial health for the organisation";

    static {
        CLAIM_AMY = new EditExpensesDescriptorBuilder().withExpensesAmount(VALID_EXPENSESAMOUNT_AMY)
                .withTravelExpenses(VALID_TRAVELEXPENSES_AMY).withMedicalExpenses(VALID_MEDICALEXPENSES_AMY)
                .withMiscellaneousExpenses(VALID_MISCELLANEOUSEXPENSES_AMY).build();
        CLAIM_BOB = new EditExpensesDescriptorBuilder().withExpensesAmount(VALID_EXPENSESAMOUNT_BOB)
                .withTravelExpenses(VALID_TRAVELEXPENSES_BOB).withMedicalExpenses(VALID_MEDICALEXPENSES_BOB)
                .withMiscellaneousExpenses(VALID_MISCELLANEOUSEXPENSES_BOB).build();
    }

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    static {
        PAY_AMY = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_AMY).withBonus(VALID_BONUS_AMY).build();
        PAY_BOB = new ModSalaryDescriptorBuilder().withSalary(VALID_SALARY_BOB).withBonus(VALID_BONUS_BOB).build();
    }

    static {
        PAY_ALL = new ModAllSalaryDescriptorBuilder().withSalary(VALID_SALARY_AMY).withBonus(VALID_BONUS_AMY).build();
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) throws ParseException {
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
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(person.getName().fullName)));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);

        Set<ModelTypes> set = new HashSet<>();
        set.add(ModelTypes.ADDRESS_BOOK);

        model.commitMultipleLists(set);
        //model.commitAddressBook();
    }


    /**
     * Updates {@code model}'s filtered list to show only the schedule at the given {@code targetIndex} in the
     * {@code model}'s schedule list.
     */
    public static void showScheduleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredScheduleList().size());

        Schedule schedule = model.getFilteredScheduleList().get(targetIndex.getZeroBased());
        model.updateFilteredScheduleList(new EmployeeIdScheduleContainsKeywordsPredicate(
                Arrays.asList(schedule.getEmployeeId().value)));

        assertEquals(1, model.getFilteredScheduleList().size());
    }

    /**
     * Deletes the first schedule in {@code model}'s filtered list from {@code model}'s schedule list.
     */
    public static void deleteFirstSchedule(Model model) {
        Schedule firstSchedule = model.getFilteredScheduleList().get(0);
        model.deleteSchedule(firstSchedule);
        model.commitScheduleList();
    }

    /**
     * Updates {@code model}'s filtered list to show only the recruitment post at the given {@code targetIndex} in the
     * {@code model}'s recruitment list.
     */
    public static void showRecruitmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecruitmentList().size());

        Recruitment recruitment = model.getFilteredRecruitmentList().get(targetIndex.getZeroBased());
        model.updateFilteredRecruitmentList(new PostContainsKeywordsPredicate(
                Arrays.asList(recruitment.getPost().value)));

        assertEquals(1, model.getFilteredRecruitmentList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the recruitment post at the given {@code targetIndex} in the
     * {@code model}'s recruitment list.
     */
    public static void showExpensesAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpensesList().size());

        Expenses expenses = model.getFilteredExpensesList().get(targetIndex.getZeroBased());
        model.updateFilteredExpensesList(new EmployeeIdExpensesContainsKeywordsPredicate(
                Arrays.asList(expenses.getEmployeeId().value)));

        assertEquals(1, model.getFilteredExpensesList().size());
    }
}
