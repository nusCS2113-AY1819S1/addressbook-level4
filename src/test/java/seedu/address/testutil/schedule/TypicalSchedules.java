package seedu.address.testutil.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleList;

/**
 * A utility class containing a list of {@code schedule} objects to be used in tests.
 */
public class TypicalSchedules {

    public static final String VALID_TYPE_ALICE = "WORK";
    public static final String VALID_DATE_ALICE = "01/01/2099";
    public static final String VALID_YEAR_ALICE = "2099";
    public static final String VALID_EMPLOYEEID_ALICE = "000001";

    public static final Schedule ALICE_WORK = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("01/01/2099").withType("WORK").build();
    public static final Schedule BENSON_WORK = new ScheduleBuilder().withEmployeeId("000002")
            .withDate("10/10/2050").withType("WORK").build();
    public static final Schedule CARL_WORK = new ScheduleBuilder().withEmployeeId("000003")
            .withDate("20/03/2099").withType("WORK").build();
    public static final Schedule DANIEL_LEAVE = new ScheduleBuilder().withEmployeeId("000004")
            .withDate("04/04/2099").withType("LEAVE").build();
    public static final Schedule ELLE_LEAVE = new ScheduleBuilder().withEmployeeId("000005")
            .withDate("05/05/2099").withType("LEAVE").build();
    public static final Schedule FIONA_LEAVE = new ScheduleBuilder().withEmployeeId("000006")
            .withDate("06/06/2059").withType("LEAVE").build();
    public static final Schedule GEORGE_LEAVE = new ScheduleBuilder().withEmployeeId("000007")
            .withDate("07/07/2059").withType("LEAVE").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Schedule BENSON_WORK_COPY = new ScheduleBuilder().withEmployeeId("000002")
            .withDate("10/10/2050").withType("WORK").build();
    public static final Schedule AMY = new ScheduleBuilder().withEmployeeId("000009")
            .withDate("01/01/2099").withType("WORK").build();
    public static final Schedule BOB = new ScheduleBuilder().withEmployeeId("000011")
            .withDate("01/01/2099").withType("WORK").build();
    public static final Schedule ALICE_LEAVE = new ScheduleBuilder().withEmployeeId("000001")
            .withDate("01/01/2099").withType("LEAVE").build();

    // Valid dates
    public static final Date MONDAY_16_JUN_2025 = new Date("16/06/2025");
    public static final Date TUESDAY_17_JUN_2025 = new Date("17/06/2025");
    public static final Date WEDNESDAY_16_JUN_2025 = new Date("18/06/2025");
    public static final Date THURSDAY_19_JUN_2025 = new Date("19/06/2025");
    public static final Date FRIDAY_20_JUN_2025 = new Date("20/06/2025");
    public static final Date SATURDAY_21_JUN_2025 = new Date("21/06/2025");
    public static final Date SUNDAY_22_JUN_2025 = new Date("22/06/2025");

    private TypicalSchedules() {} // prevents instantiation
    /**
     * Returns an {@code ScheduleList} with all the typical schedules.
     */
    public static ScheduleList getTypicalScheduleList() {
        ScheduleList sl = new ScheduleList();
        for (Schedule schedule : getTypicalSchedules()) {
            sl.addSchedule(schedule);
        }
        return sl;
    }

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(ALICE_WORK, BENSON_WORK, CARL_WORK, DANIEL_LEAVE,
                ELLE_LEAVE, FIONA_LEAVE, GEORGE_LEAVE));
    }

}
