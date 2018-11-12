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

public class AddLeavesCommandTest {

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
        new AddLeavesCommand(null);
    }

    @Test
    public void execute_noEmployeeFound_throwsCommandException() throws Exception {
        //0 employees from new address book
        Model model = new ModelManager(new AddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        AddLeavesCommand addLeavesCommand = new AddLeavesCommand(weekDaySet);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddLeavesCommand.MESSAGE_NO_PERSON_OBSERVED,
                FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, FilterCommand.COMMAND_WORD));
        addLeavesCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_scheduleAcceptedByModel_addSuccessful() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        Set<Date> dateSet = new HashSet<>();
        dateSet.add(MONDAY_16_JUN_2025);

        // 7 employee scheduled work on weekdays -> all success
        CommandResult commandResult = new AddLeavesCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddLeavesCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);
    }

    /**
     * Scheduling multiple leaves for multiple employees that exists same day leave schedule.
     * Schedule leave on all overlapping leave schedule.
     * 1) Condition: Schedule 5 leave schedules on weekday Monday to Friday.
     * 2) Schedule the same 5 leave schedules on weekday Monday to Friday. -> throws exception
     * @throws Exception
     */
    @Test
    public void execute_scheduleLeaveOnAllOverlappingLeaveSchedule_throwsCommandException() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled work on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all success
        CommandResult commandResult = new AddLeavesCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddLeavesCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);

        // 7 employee scheduled work again on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all fail throw exception
        AddLeavesCommand addLeavesCommand = new AddLeavesCommand(weekDaySet);
        Assert.assertThrows(CommandException.class, () -> addLeavesCommand.execute(model, commandHistory));
        assertCommandFailure(addLeavesCommand, model,
                commandHistory, String.format(AddLeavesCommand.MESSAGE_PERSON_ALL_ADDED_LEAVE, weekDaySet));
    }

    /**
     * Scheduling multiple leave for multiple employees that exists same day work schedule.
     * Scheduling leave on all overlapping work schedule.
     * 1) Condition: Schedule 5 work schedules date from Monday to Friday.
     * 2) Schedule the same 3 leave schedules with similar date Mon,Wed,Fri-> throws exception
     * @throws Exception
     */
    @Test
    public void execute_scheduleLeaveOnAllOverlappingWorkSchedule_throwsCommandException() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled work on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all success
        CommandResult commandResult = new AddWorksCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddWorksCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);

        // 7 employee scheduled leave again on MON, WED, FRI -> fail throw exception
        Multimap<EmployeeId, Date> employeeIdMapToWorks = getMutlimapIdToWorks(weekDaySubset, model);
        AddLeavesCommand addLeavesCommand = new AddLeavesCommand(weekDaySubset);
        Assert.assertThrows(CommandException.class, () -> addLeavesCommand.execute(model, commandHistory));
        assertCommandFailure(addLeavesCommand, model,
                commandHistory, AddLeavesCommand.getUserInteractionFeedback(
                        employeeIdMapToWorks, false, weekDaySubset));
    }


    /**
     * Scheduling multiple leave for multiple employees that exists same day work schedule.
     * Scheduling leave on some overlapping work schedule.
     * 1) Condition: Schedule 5 work schedules date from Monday to Friday.
     * 2) Schedule the similar 7 leave schedules with similar date Monday to Sunday
     *              -> throws exception for Mon to fri but adds Saturday and Sunday's schedule
     * @throws Exception
     */
    @Test
    public void execute_scheduleLeaveOnSomeOverlappingWorkSchedule_throwsCommandException() throws Exception {
        //7 employees from typical address book
        Model model = new ModelManager(getTypicalAddressBook(), new ExpensesList(),
                new ScheduleList(), new RecruitmentList(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();

        // 7 employee scheduled work on MONDAY_16_JUN_2025 to FRIDAY_20_JUN_2025 -> all success
        CommandResult commandResult = new AddWorksCommand(weekDaySet).execute(model, commandHistory);
        assertEquals(String.format(AddWorksCommand.MESSAGE_SUCCESS_ALL_ADDED, weekDaySet),
                commandResult.feedbackToUser);

        // 7 employee scheduled leave again on MONDAY_16_JUN_2025 to SUNDAY_22_JUN_2025 -> some fail throw exception
        Multimap<EmployeeId, Date> employeeIdMapToWorks = getMutlimapIdToWorks(wholeWeekSet, model);
        AddLeavesCommand addLeavesCommand = new AddLeavesCommand(wholeWeekSet);
        commandResult = addLeavesCommand.execute(model, commandHistory);
        assertEquals(AddLeavesCommand.getUserInteractionFeedback(
                employeeIdMapToWorks, true, wholeWeekSet),
                commandResult.feedbackToUser);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AddLeavesCommand weekDays = new AddLeavesCommand(weekDaySet);
        // same object -> returns true
        assertTrue(weekDays.equals(weekDays));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AddLeavesCommand weekDays = new AddLeavesCommand(weekDaySet);
        // same values -> returns true
        AddLeavesCommand weekDaysCopy = new AddLeavesCommand(weekDaySet);
        assertTrue(weekDays.equals(weekDaysCopy));
    }

    @Test
    public void equals_sameTypes_returnsFalse() {
        AddLeavesCommand weekDays = new AddLeavesCommand(weekDaySet);
        // different types -> returns false
        assertFalse(weekDays.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        AddLeavesCommand weekDays = new AddLeavesCommand(weekDaySet);
        // null -> returns false
        assertFalse(weekDays == null);
    }

    @Test
    public void equals_differentDays_returnsFalse() {
        AddLeavesCommand weekDays = new AddLeavesCommand(weekDaySet);
        AddLeavesCommand weekEnds = new AddLeavesCommand(weekEndSet);
        // different days -> returns false
        assertFalse(weekDays.equals(weekEnds));
    }

    /**
     * Get List of Employee Id map to works
     * @param setOfDates
     * @param model
     * @return
     */
    private Multimap<EmployeeId, Date> getMutlimapIdToWorks (Set<Date> setOfDates, Model model) {
        Type work = new Type(Type.WORK);
        Multimap<EmployeeId, Date> employeeIdMapToWorks = TreeMultimap.create(
                new EmployeeIdComparator(), new DateComparator());

        for (Date date :setOfDates) {
            for (Person person : model.getFilteredPersonList()) {
                Schedule hasWorkSchedule = new Schedule(person.getEmployeeId(), work , date);
                if (model.hasSchedule(hasWorkSchedule)) {
                    employeeIdMapToWorks.put(person.getEmployeeId(), date);
                }
            }
        }
        return employeeIdMapToWorks;
    }

}
