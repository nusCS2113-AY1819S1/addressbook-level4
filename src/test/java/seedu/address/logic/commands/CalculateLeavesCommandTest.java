package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_DAISY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.schedule.TypicalSchedules.FRIDAY_20_JUN_2025;
import static seedu.address.testutil.schedule.TypicalSchedules.MONDAY_16_JUN_2025;
import static seedu.address.testutil.schedule.TypicalSchedules.SATURDAY_21_JUN_2025;
import static seedu.address.testutil.schedule.TypicalSchedules.SUNDAY_22_JUN_2025;
import static seedu.address.testutil.schedule.TypicalSchedules.THURSDAY_19_JUN_2025;
import static seedu.address.testutil.schedule.TypicalSchedules.TUESDAY_17_JUN_2025;
import static seedu.address.testutil.schedule.TypicalSchedules.WEDNESDAY_16_JUN_2025;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.model.schedule.Year;

public class CalculateLeavesCommandTest {

    public static final Year YEAR_2025 = new Year("2025");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Set<Date> wholeWeekSet = new HashSet<>();
    private Set<Date> weekDaySet = new HashSet<>();
    private Set<Date> weekDaySubset = new HashSet<>();
    private Set<Date> weekEndSet = new HashSet<>();

    @Before
    public void runBeforeTestMethod() {
        weekDaySet.add(MONDAY_16_JUN_2025);
        weekDaySet.add(TUESDAY_17_JUN_2025);
        weekDaySet.add(WEDNESDAY_16_JUN_2025);
        weekDaySet.add(THURSDAY_19_JUN_2025);
        weekDaySet.add(FRIDAY_20_JUN_2025);

        weekDaySubset.add(MONDAY_16_JUN_2025);
        weekDaySubset.add(WEDNESDAY_16_JUN_2025);
        weekDaySubset.add(FRIDAY_20_JUN_2025);

        weekEndSet.add(SATURDAY_21_JUN_2025);
        weekEndSet.add(SUNDAY_22_JUN_2025);

        wholeWeekSet.add(MONDAY_16_JUN_2025);
        wholeWeekSet.add(TUESDAY_17_JUN_2025);
        wholeWeekSet.add(WEDNESDAY_16_JUN_2025);
        wholeWeekSet.add(THURSDAY_19_JUN_2025);
        wholeWeekSet.add(FRIDAY_20_JUN_2025);
        wholeWeekSet.add(SATURDAY_21_JUN_2025);
        wholeWeekSet.add(SUNDAY_22_JUN_2025);
    }

    @Test
    public void constructor_nullYearNullId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CalculateLeavesCommand(null, null);
    }

    @Test
    public void execute_noEmployeeFound_throwsCommandException() throws Exception {
        //7 employees from new address book, each employee scheduled with leave for weekday Mon-Fri.
        Model model = getModelWeekday();
        CommandHistory commandHistory = new CommandHistory();

        //8th employee not inside address book with employee id 999999
        EmployeeId employeeIdDaisy = new EmployeeId(VALID_EMPLOYEEID_DAISY);
        CalculateLeavesCommand calculateLeavesCommand =
                new CalculateLeavesCommand(employeeIdDaisy, YEAR_2025);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(CalculateLeavesCommand.MESSAGE_EMPLOYEE_ID_NOT_FOUND,
                FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        calculateLeavesCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_noScheduleFound_throwsCommandException() throws Exception {
        //7 employees from new address book, 0 schedules each
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        //No schedule found for alice -> throws exception no schedule found
        CalculateLeavesCommand calculateLeavesCommand =
                new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(CalculateLeavesCommand.MESSAGE_NO_SCHEDULE_FOUND,
                FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        calculateLeavesCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_leaveScheduleFoundInYear_calculateNumberOfLeavesSuccessful() throws Exception {
        //7 employees from new address book, each employee scheduled with leave for weekday Mon-Fri.
        Model model = getModelWeekday();
        CommandHistory commandHistory = new CommandHistory();

        //Calculate leaves for employee id 000001 in year 2025
        CommandResult commandResult = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025)
                .execute(model, commandHistory);

        assertEquals(String.format(CalculateLeavesCommand.MESSAGE_SUCCESS,
                ALICE.getEmployeeId(), YEAR_2025, 5), commandResult.feedbackToUser);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        CalculateLeavesCommand aliceLeave = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);
        // same object -> returns true
        assertTrue(aliceLeave.equals(aliceLeave));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        CalculateLeavesCommand aliceLeave = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);
        // same values -> returns true
        CalculateLeavesCommand aliceLeaveCopy = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);
        assertTrue(aliceLeave.equals(aliceLeaveCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        CalculateLeavesCommand aliceLeave = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);
        // different types -> returns false
        assertFalse(aliceLeave.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        CalculateLeavesCommand aliceLeave = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);
        // null -> returns false
        assertFalse(aliceLeave == null);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        CalculateLeavesCommand aliceLeave = new CalculateLeavesCommand(ALICE.getEmployeeId(), YEAR_2025);
        CalculateLeavesCommand bensonLeave = new CalculateLeavesCommand(BENSON.getEmployeeId(), YEAR_2025);
        // different days -> returns false
        assertFalse(aliceLeave.equals(bensonLeave));
    }

    /**
     * Gets a model with 7 employees, each employee scheduled with leave for weekday Mon-Fri,
     * with work for Sat-Sun.
     * @return model
     * @throws Exception
     */
    private Model getModelWeekday() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        new AddLeavesCommand(weekDaySet).execute(model, commandHistory);
        new AddWorksCommand(weekEndSet).execute(model, commandHistory);

        return model;
    }

}
