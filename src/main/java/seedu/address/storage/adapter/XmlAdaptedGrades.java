package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.grades.Grades;

/**
 * JAXB-friendly adapted version of the Gradebook.
 */
@XmlRootElement(name = "gradeInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAdaptedGrades {
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String moduleCode;
    @XmlElement(name = "gradeComponentName", required = true, nillable = true)
    private String gradeComponentName;
    @XmlElement(name = "adminNo", required = true, nillable = true)
    private String adminNo;
    @XmlElement(name = "studentMarks", required = true, nillable = true)
    private int studentMarks;

    /**
     * Constructs an XmlAdaptedGrades
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGrades() {

    }

    /**
     * Constructs an {@code XmlAdaptedGrades} with the given details
     */
    public XmlAdaptedGrades(Grades grades) {
        this.moduleCode = grades.getModuleCode();
        this.gradeComponentName = grades.getGradeComponentName();
        this.adminNo = grades.getAdminNo();
        this.studentMarks = grades.getMarks();
    }

    /**
     * Converts this XmlAdaptedGrades into the model's Grades object
     */
    public Grades toGradeType() {
        return new Grades(moduleCode, gradeComponentName, adminNo, studentMarks);
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getGradeComponentName() {
        return gradeComponentName;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public int getStudentMarks() {
        return studentMarks;
    }
}
