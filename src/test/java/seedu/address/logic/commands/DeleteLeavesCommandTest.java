package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.testutil.Assert;

public class DeleteLeavesCommandTest {

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
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteLeavesCommand(null);
    }

    @Test
    public void execute_noEmployeeFound_throwsCommandException() throws Exception {
        //0 employees from new address book
        Model model = new ModelManager(new AddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        DeleteLeavesCommand deleteLeavesCommand = new DeleteLeavesCommand(weekDaySet);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(DeleteLeavesCommand.MESSAGE_NO_PERSON_FOUND,
                FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        deleteLeavesCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_deleteWeekdayLeaveFromAllWeekdays_deleteSuccessful() throws Exception {
        //Gets a model with 7 employees, each employee scheduled with leave for weekdays Mon-Fri.
        Model model = getModelWeekday();
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee deleted scheduled leave on weekday Mon, Wed, Fri -> success
        CommandResult commandResult = new DeleteLeavesCommand(weekDaySubset).execute(model, commandHistory);
        assertEquals(String.format(DeleteLeavesCommand.MESSAGE_SUCCESS, weekDaySubset),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_deleteWeekendLeaveFromAllWeekdays_throwsCommandException() throws Exception {
        //Gets a model with 7 employees, each employee scheduled with leave for weekdays Mon-Fri.
        Model model = getModelWeekday();
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee deleted scheduled leave on weekend sat and sun -> throws Command Exception
        DeleteLeavesCommand deleteLeavesCommand = new DeleteLeavesCommand(weekEndSet);
        Assert.assertThrows(CommandException.class, () -> deleteLeavesCommand.execute(model, commandHistory));
        assertCommandFailure(deleteLeavesCommand, model,
                commandHistory, String.format(DeleteLeavesCommand.MESSAGE_PERSON_ALL_DELETED_LEAVE, weekEndSet));
    }

    /**
     *  7 employee deleted scheduled leaves on weekday Mon to Fri
     *  Deletes schedule from Mon to Sun for all 7 employees -> success delete employee
     *  So long 1 employee contains schedule that can be deleted, it will be success as it deletes
     *  employees that contains the date, as written in user guide.
     * @throws Exception
     */
    @Test
    public void execute_deleteWholeWeekWorkFromAllWeekdays_throwsCommandException() throws Exception {
        //Gets a model with 7 employees, each employee scheduled with work for weekdays Mon-Fri.
        Model model = getModelWeekday();
        CommandHistory commandHistory = new CommandHistory();

        CommandResult commandResult = new DeleteLeavesCommand(wholeWeekSet).execute(model, commandHistory);
        assertEquals(String.format(DeleteLeavesCommand.MESSAGE_SUCCESS, wholeWeekSet),
                commandResult.feedbackToUser);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeleteLeavesCommand weekDays = new DeleteLeavesCommand(weekDaySet);
        // same object -> returns true
        assertTrue(weekDays.equals(weekDays));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        DeleteLeavesCommand weekDays = new DeleteLeavesCommand(weekDaySet);
        // same values -> returns true
        DeleteLeavesCommand weekDaysCopy = new DeleteLeavesCommand(weekDaySet);
        assertTrue(weekDays.equals(weekDaysCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        DeleteLeavesCommand weekDays = new DeleteLeavesCommand(weekDaySet);
        // different types -> returns false
        assertFalse(weekDays.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        DeleteLeavesCommand weekDays = new DeleteLeavesCommand(weekDaySet);
        // null -> returns false
        assertFalse(weekDays == null);
    }

    @Test
    public void equals_differentDays_returnsFalse() {
        DeleteLeavesCommand weekDays = new DeleteLeavesCommand(weekDaySet);
        DeleteLeavesCommand weekEnds = new DeleteLeavesCommand(weekEndSet);
        // different days -> returns false
        assertFalse(weekDays.equals(weekEnds));
    }

    /**
     * Gets a model with 7 employees, each employee scheduled with leave for weekday Mon-Fri.
     * @return model
     * @throws Exception
     */
    private Model getModelWeekday() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        new AddLeavesCommand(weekDaySet).execute(model, commandHistory);

        return model;
    }

}
