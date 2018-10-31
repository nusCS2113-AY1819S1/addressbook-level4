package seedu.address.storage.adapter;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * JAXB-friendly adapted version of the ClassroomAttendance.
 */
public class XmlAdaptedClassroomAttendance {
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
}
