package seedu.address.model.gradebook;

/**
 This class includes all necessary validation for gradebook objects.
 */
public class Gradebook {
    private String moduleCode;
    private String gradebookComponentName;
    private String studentAdminNo;
    private String gradebookNewComponentName;
    private int gradebookMaxMarks;
    private int gradebookWeightage;
    private int studentGrade;

    public Gradebook(
            String moduleCode,
            String gradebookComponentName,
            int gradebookMaxMarks,
            int gradebookWeightage) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
        this.gradebookMaxMarks = gradebookMaxMarks;
        this.gradebookWeightage = gradebookWeightage;
    }

    public Gradebook(
            String moduleCode,
            String gradebookComponentName,
            String gradebookNewComponentName,
            int gradebookMaxMarks,
            int gradebookWeightage) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
        this.gradebookNewComponentName = gradebookNewComponentName;
        this.gradebookMaxMarks = gradebookMaxMarks;
        this.gradebookWeightage = gradebookWeightage;
    }

    public Gradebook(
            String studentAdminNo,
            String moduleCode,
            String gradebookComponentName,
            int studentGrade) {
        this.studentAdminNo = studentAdminNo;
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
        this.studentGrade = studentGrade;
    }

    public Gradebook(String moduleCode, String gradebookComponentName) {
        this.moduleCode = moduleCode;
        this.gradebookComponentName = gradebookComponentName;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getgradebookNewComponentName() {
        return this.gradebookNewComponentName;
    }

    public String getGradeComponentName() {
        return this.gradebookComponentName;
    }

    public void setGradeComponentName (String gradebookComponentName) {
        this.gradebookComponentName = gradebookComponentName;
    }

    public int getGradeComponentMaxMarks() {
        return this.gradebookMaxMarks;
    }

    public void setgradebookMaxMarks (int gradebookMaxMarks) {
        this.gradebookMaxMarks = gradebookMaxMarks;
    }

    public int getGradeComponentWeightage() {
        return this.gradebookWeightage;
    }

    public void setgradebookWeightage (int gradebookWeightage) {
        this.gradebookWeightage = gradebookWeightage;
    }

    public String getStudentAdminNo() {
        return this.studentAdminNo;
    }

    public void setStudentAdminNo (String studentAdminNo) {
        this.studentAdminNo = studentAdminNo;
    }

    public int getStudentGrade() {
        return this.studentGrade;
    }

    public void setStudentGrade (int studentGrade) {
        this.studentGrade = studentGrade;
    }
}
