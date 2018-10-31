package seedu.address.storage.serializable;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.storage.adapter.XmlAdaptedClassroomAttendance;

/**
 * Represents a list of classroom attendance that is serializable to XML format
 */
@XmlRootElement(namespace = "seedu.address.storage.serializable")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableClassroomAttendanceList {
    @XmlElementWrapper(name = "attendanceList")
    @XmlElement(name = "attendance")
    private ArrayList<XmlAdaptedClassroomAttendance> classroomAttendanceList;

    /**
     * Initializes an empty list of classroom attendance
     */
    public XmlSerializableClassroomAttendanceList() {
        classroomAttendanceList = new ArrayList<>();
    }

    public ArrayList<XmlAdaptedClassroomAttendance> getClassroomAttendanceList() {
        return classroomAttendanceList;
    }

    public void setClassroomAttendanceList(ArrayList<XmlAdaptedClassroomAttendance> classroomAttendanceList) {
        this.classroomAttendanceList = classroomAttendanceList;
    }
}
