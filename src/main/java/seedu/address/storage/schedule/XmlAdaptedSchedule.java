package seedu.address.storage.schedule;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;

/**
 * JAXB-friendly version of the Schedule.
 */
public class XmlAdaptedSchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "schedule's %s field is missing!";

    @XmlElement(required = true)
    private String employeeId;
    @XmlElement(required = true)
    private String type;
    @XmlElement(required = true)
    private String date;

    /**
     * Constructs an XmlAdaptedSchedule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSchedule() {}

    /**
     * Constructs an {@code XmlAdaptedSchedule} with the given schedule details.
     */
    public XmlAdaptedSchedule(String employeeId, String type, String date) {
        this.employeeId = employeeId;
        this.type = type;
        this.date = date;
    }

    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSchedule
     */
    public XmlAdaptedSchedule(Schedule source) {
        employeeId = source.getEmployeeId().value;
        date = source.getScheduleDate().value;
        type = source.getType().value;
    }

    /**
     * Converts this jaxb-friendly adapted schedule object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule
     */
    public Schedule toModelType() throws IllegalValueException {

        if (employeeId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeId.class.getSimpleName()));
        }
        if (!EmployeeId.isValidEmployeeId(employeeId)) {
            throw new IllegalValueException(EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);
        }
        final EmployeeId modelEmployeeId = new EmployeeId(employeeId);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_TYPE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidScheduleDate(date)) {
            throw new IllegalValueException(Date.getDateConstraintsError());
        }
        final Date modelDate = new Date(date);

        return new Schedule(modelEmployeeId, modelType, modelDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSchedule)) {
            return false;
        }

        XmlAdaptedSchedule otherScehdule = (XmlAdaptedSchedule) other;
        return Objects.equals(employeeId, otherScehdule.employeeId)
                && Objects.equals(date, otherScehdule.date)
                && Objects.equals(type, otherScehdule.type);
    }
}
