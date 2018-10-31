package seedu.address.storage.adapter;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.classroom.Attendance;
import seedu.address.model.person.MatricNo;

/**
 * JAXB-friendly adapted version of the ClassroomAttendance.
 */
public class XmlAdaptedClassroomAttendance {
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Classroom attendance's %s field is missing!";

    //class-attendance fields
    @XmlElement(name = "date", required = true, nillable = true)
    private String date;
    @XmlElement(name = "className", required = true, nillable = true)
    private String className;
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String moduleCode;
    @XmlElementWrapper(name = "studentsPresent")
    @XmlElement(name = "matricNo", required = true, nillable = true)
    private ArrayList<String> studentsPresent;

    /**
     * Constructs an XmlAdaptedClassroomAttendance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedClassroomAttendance() {
    }

    /**
     * Constructs an {@code XmlAdaptedClassroomAttendance} with the given classroom attendance details
     */
    public XmlAdaptedClassroomAttendance(String className, String moduleCode, String date,
                                         ArrayList<String> studentsPresent) {
        this.className = className;
        this.moduleCode = moduleCode;
        this.date = date;
        this.studentsPresent = studentsPresent;
    }

    public String getDate() {
        return date;
    }

    public String getClassName() {
        return className;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public ArrayList<String> getStudentsPresent() {
        return studentsPresent;
    }

    /**
     * Converts this XmlAdaptedClassroomAttendance into the model's Attendance object
     */
    public Attendance toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Attendance.class.getSimpleName()));
        }

        if (!Attendance.isValidDate(date)) {
            throw new IllegalValueException(Attendance.MESSAGE_DATE_CONSTRAINTS);
        }

        ArrayList<String> modelStudentsPresent = new ArrayList<>();

        for (String matricNo : studentsPresent) {
            if (MatricNo.isValidMatricNo(matricNo)) {
                modelStudentsPresent.add(matricNo);
            }
        }

        return new Attendance(date, modelStudentsPresent);
    }
}
