package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import seedu.address.model.module.ModuleCode;
import seedu.address.ui.HtmlTableProcessor;

/**
 * Represents a classroom in the app.
 */
public class Classroom {
    private ClassName className;
    private ModuleCode moduleCode;
    private Enrollment maxEnrollment;
    private ArrayList<String> students = new ArrayList<>();
    private ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();

    public Classroom(ClassName className, ModuleCode moduleCode, Enrollment maxEnrollment) {
        requireNonNull(className);
        this.className = className;
        this.moduleCode = moduleCode;
        this.maxEnrollment = maxEnrollment;
    }

    public ClassName getClassName() {
        return className;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Enrollment getMaxEnrollment() {
        return maxEnrollment;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(ArrayList<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Class name: ")
                .append(getClassName())
                .append(" Module code: ")
                .append(getModuleCode())
                .append(" Max Enrollment: ")
                .append(getMaxEnrollment());
        return builder.toString();
    }

    /**
     * Converts class into html string
     */
    public String toClassHtmlString() {
        return HtmlTableProcessor
                .renderTableItem(new ArrayList<>(Arrays
                        .asList(className.getValue(),
                                moduleCode.moduleCode,
                                maxEnrollment.getValue())));
    }

    /**
     * Returns true if both classrooms of the same className have the same moduleCode.
     * This defines a weaker notion of equality between two classrooms.
     */
    public boolean isSameClassroom(Classroom otherClassroom) {
        if (otherClassroom == this) {
            return true;
        }

        return otherClassroom != null
                && otherClassroom.getClassName().equals(getClassName())
                && (otherClassroom.getModuleCode().equals(getModuleCode()));
    }

    /**
     * Returns true if both classrooms have the same identity and data fields.
     * This defines a stronger notion of equality between two classrooms.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Classroom)) {
            return false;
        }

        Classroom otherClassroom = (Classroom) other;
        return otherClassroom.getClassName().equals(getClassName())
                && otherClassroom.getModuleCode().equals(getModuleCode())
                && otherClassroom.getMaxEnrollment().equals(getMaxEnrollment());
    }

    /**
     * Returns true if both classroom have the same class-specific fields.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(className, moduleCode, maxEnrollment);
    }
}
