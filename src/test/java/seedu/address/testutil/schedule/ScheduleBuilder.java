package seedu.address.testutil.schedule;

import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;

/**
 * A utility class to help with building schedule objects for tests.
 */
public class ScheduleBuilder {

    public static final String DEFAULT_EMPLOYEEID = "000001";
    public static final String DEFAULT_TYPE = "LEAVE";
    public static final String DEFAULT_DATE = "02/02/2099";

    // Data fields
    private EmployeeId employeeId;
    private Type type;
    private Date date;

    public ScheduleBuilder () {
        employeeId = new EmployeeId(DEFAULT_EMPLOYEEID);
        type = new Type(DEFAULT_TYPE);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Initializes the ScheduleBuilder with the data of {@code scheduleToCopy}.
     */
    public ScheduleBuilder (Schedule scheduleToCopy) {
        employeeId = scheduleToCopy.getEmployeeId();
        type = scheduleToCopy.getType();
        date = scheduleToCopy.getScheduleDate();
    }


    /**
     * Sets the {@code EmployeeId} of the {@code schedule} that we are building.
     */
    public ScheduleBuilder withEmployeeId(String employeeId) {
        this.employeeId = new EmployeeId(employeeId);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code schedule} that we are building.
     */
    public ScheduleBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code schedule} that we are building.
     */
    public ScheduleBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Builds (@code schedule) with required schedule's variables
     */
    public Schedule build() {
        return new Schedule (employeeId, type, date);
    }


}
