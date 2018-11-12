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

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.DateComparator;
import seedu.address.model.schedule.EmployeeIdComparator;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.model.schedule.Type;
import seedu.address.testutil.Assert;


public class AddWorksCommandTest {

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
        new AddWorksCommand(null);
    }

    @Test
    public void execute_noEmployeeFound_throwsCommandException() throws Exception {
        //0 employees from new address book
        Model model = new ModelManager(new AddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        AddWorksCommand addWorksCommand = new AddWorksCommand(weekDaySet);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddWorksCommand.MESSAGE_NO_PERSON_OBSERVED,
                FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        addWorksCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_scheduleAcceptedByModel_addSuccessful() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled work on weekdays -> all success
        CommandResult commandResult = new AddWorksCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddWorksCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);
    }

    /**
     * Scheduling multiple work for multiple employees that exists same day work schedule.
     * Schedule work on all overlapping work schedule.
     * 1) Condition: Schedule 5 work schedules on weekday Monday to Friday.
     * 2) Schedule the same 5 work schedules on weekday Monday to Friday. -> throws exception
     * @throws Exception
     */
    @Test
    public void execute_scheduleWorkOnAllOverlappingWorkSchedule_throwsCommandException() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled work on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all success
        CommandResult commandResult = new AddWorksCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddWorksCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);

        // 7 employee scheduled work again on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all fail throw exception
        AddWorksCommand addWorksCommand = new AddWorksCommand(weekDaySet);
        Assert.assertThrows(CommandException.class, () -> addWorksCommand.execute(model, commandHistory));
        assertCommandFailure(addWorksCommand, model,
                commandHistory, String.format(AddWorksCommand.MESSAGE_PERSON_ALL_ADDED_WORK, weekDaySet));
    }


    /**
     * Scheduling multiple work for multiple employees that exists same day leave schedule.
     * Scheduling work on all overlapping leave schedule.
     * 1) Condition: Schedule 5 leave schedules date from Monday to Friday.
     * 2) Schedule the same 3 work schedules with similar date Mon,Wed,Fri-> throws exception
     * @throws Exception
     */
    @Test
    public void execute_scheduleWorkOnAllOverlappingLeaveSchedule_throwsCommandException() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled leave on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all success
        CommandResult commandResult = new AddLeavesCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddLeavesCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);

        // 7 employee scheduled work again on MON, WED, FRI -> fail throw exception
        Multimap<EmployeeId, Date> employeeIdMapToLeaves = getMutlimapIdToLeave(weekDaySubset, model);
        AddWorksCommand addWorksCommand = new AddWorksCommand(weekDaySubset);
        Assert.assertThrows(CommandException.class, () -> addWorksCommand.execute(model, commandHistory));
        assertCommandFailure(addWorksCommand, model,
                commandHistory, AddWorksCommand.getUserInteractionFeedback(
                        employeeIdMapToLeaves, false, weekDaySubset));
    }


    /**
     * Scheduling multiple work for multiple employees that exists same day leave schedule.
     * Scheduling work on some overlapping leave schedule.
     * 1) Condition: Schedule 5 leave schedules date from Monday to Friday.
     * 2) Schedule the similar 7 work schedules with similar date Monday to Sunday
     *              -> throws exception for Mon to fri but adds Saturday and Sunday's schedule
     * @throws Exception
     */
    @Test
    public void execute_scheduleWorkOnSomeOverlappingLeaveSchedule_throwsCommandException() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled leave on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all success
        CommandResult commandResult = new AddLeavesCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddLeavesCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);

        // 7 employee scheduled work again on MONDAY_16_JUN_2025 to SUNDAY_22_JUN_2025 -> some fail throw exception
        Multimap<EmployeeId, Date> employeeIdMapToLeaves = getMutlimapIdToLeave(wholeWeekSet, model);
        AddWorksCommand addWorksCommand = new AddWorksCommand(wholeWeekSet);
        commandResult = addWorksCommand.execute(model, commandHistory);
        assertEquals(AddWorksCommand.getUserInteractionFeedback(
                employeeIdMapToLeaves, true, wholeWeekSet),
                commandResult.feedbackToUser);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AddWorksCommand weekDays = new AddWorksCommand(weekDaySet);
        // same object -> returns true
        assertTrue(weekDays.equals(weekDays));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AddWorksCommand weekDays = new AddWorksCommand(weekDaySet);
        // same values -> returns true
        AddWorksCommand weekDaysCopy = new AddWorksCommand(weekDaySet);
        assertTrue(weekDays.equals(weekDaysCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        AddWorksCommand weekDays = new AddWorksCommand(weekDaySet);
        // different types -> returns false
        assertFalse(weekDays.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        AddWorksCommand weekDays = new AddWorksCommand(weekDaySet);
        // null -> returns false
        assertFalse(weekDays == null);
    }

    @Test
    public void equals_differentDays_returnsFalse() {
        AddWorksCommand weekDays = new AddWorksCommand(weekDaySet);
        AddWorksCommand weekEnds = new AddWorksCommand(weekEndSet);
        // different days -> returns false
        assertFalse(weekDays.equals(weekEnds));
    }

    /**
     * Get List of Employee Id map to leaves
     * @param setOfDates
     * @param model
     * @return
     */
    private Multimap<EmployeeId, Date> getMutlimapIdToLeave (Set<Date> setOfDates, Model model) {
        Type leave = new Type(Type.LEAVE);
        Multimap<EmployeeId, Date> employeeIdMapToLeaves = TreeMultimap.create(
                new EmployeeIdComparator(), new DateComparator());

        for (Date date :setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule hasLeaveSchedule = new Schedule(person.getEmployeeId(), leave , date);
                if (model.hasSchedule(hasLeaveSchedule)) {
                    employeeIdMapToLeaves.put(person.getEmployeeId(), date);
                }
            }
        }
        return employeeIdMapToLeaves;
    }

}
