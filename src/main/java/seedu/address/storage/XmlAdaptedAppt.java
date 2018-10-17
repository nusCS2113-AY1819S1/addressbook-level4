package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timetable.Appt;
import seedu.address.model.timetable.ApptDateTime;
import seedu.address.model.timetable.ApptDrName;
import seedu.address.model.timetable.ApptInfo;
import seedu.address.model.timetable.ApptVenue;

/**
 * JAXB-friendly version of the Appt.
 */
public class XmlAdaptedAppt {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appt's %s field is missing!";

    @XmlValue
    private String apptStart;
    @XmlValue
    private String apptEnd;
    @XmlValue
    private String apptVenue;
    @XmlValue
    private String apptInfo;
    @XmlValue
    private String apptDrName;

    /**
     * Constructs an XmlAdaptedAppt.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppt() {}

    /**
     * Constructs a {@code XmlAdaptedAppt} with the given appt details.
     */
    public XmlAdaptedAppt(String apptStart, String apptEnd, String apptVenue, String apptInfo, String apptDrName) {
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptVenue = apptVenue;
        this.apptInfo = apptInfo;
        this.apptDrName = apptDrName;
    }

    /**
     * Converts a given Appt into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppt
     */
    public XmlAdaptedAppt(Appt source) {
        apptStart = source.getStart().toString();
        apptEnd = source.getEnd().toString();
        apptVenue = source.getVenue().toString();
        apptInfo = source.getInfo().toString();
        apptDrName = source.getDrName().toString();
    }

    /**
     * Converts this jaxb-friendly adapted appt object into the model's Appt object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appt
     */
    public Appt toModelType() throws IllegalValueException {
        if (apptStart == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Appt.class.getSimpleName()));
        }
        final ApptDateTime modelApptStart = new ApptDateTime(apptStart);

        if (apptEnd == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Appt.class.getSimpleName()));
        }
        final ApptDateTime modelApptEnd = new ApptDateTime(apptEnd);

        if (apptVenue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Appt.class.getSimpleName()));
        }
        final ApptVenue modelApptVenue = new ApptVenue(apptVenue);

        if (apptInfo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Appt.class.getSimpleName()));
        }
        final ApptInfo modelApptInfo = new ApptInfo(apptInfo);

        if (apptDrName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Appt.class.getSimpleName()));
        }
        final ApptDrName modelApptDrName = new ApptDrName(apptDrName);

        return new Appt(modelApptStart, modelApptEnd, modelApptVenue, modelApptInfo, modelApptDrName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppt)) {
            return false;
        }

        XmlAdaptedAppt otherAppt = (XmlAdaptedAppt) other;
        return Objects.equals(apptStart, otherAppt.apptStart)
                && Objects.equals(apptEnd, otherAppt.apptEnd)
                && Objects.equals(apptVenue, otherAppt.apptVenue)
                && Objects.equals(apptInfo, otherAppt.apptInfo)
                && Objects.equals(apptDrName, otherAppt.apptDrName);
    }
}
