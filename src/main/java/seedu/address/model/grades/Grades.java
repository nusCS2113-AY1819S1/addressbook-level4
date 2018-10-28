package seedu.address.model.grades;

/**
 * Represents a note in Trajectory.
 */
public class Grades {
    private String moduleCode;
    private String gradeComponentName;
    private String adminNo;
    private float marks;

    public Grades(String moduleCode, String gradeComponentName, String adminNo, float marks) {
        this.moduleCode = moduleCode;
        this.gradeComponentName = gradeComponentName;
        this.adminNo = adminNo;
        this.marks = marks;
    }

    public Grades(String moduleCode, String gradeComponentName) {
        this.moduleCode = moduleCode;
        this.gradeComponentName = gradeComponentName;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getGradeComponentName() {
        return this.gradeComponentName;
    }

    public void setGradeComponentName(String gradeComponentName) {
        this.gradeComponentName = gradeComponentName;
    }

    public String getAdminNo() {
        return this.adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public float getMarks() {
        return this.marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }



}
