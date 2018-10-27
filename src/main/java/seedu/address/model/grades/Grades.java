package seedu.address.model.grades;

/**
 * Represents a note in Trajectory.
 */
public class Grades {
    private String moduleCode;
    private String gradeComponentName;
    private String adminNo;
    private int marks;

    public Grades(String moduleCode, String gradeComponentName, String adminNo, int marks) {
        this.moduleCode = moduleCode;
        this.gradeComponentName = gradeComponentName;
        this.adminNo = adminNo;
        this.marks = marks;
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

    public int getMarks() {
        return this.marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }



}
