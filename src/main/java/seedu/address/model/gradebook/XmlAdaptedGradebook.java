package seedu.address.model.gradebook;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a component in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
@XmlRootElement(name = "gradeComponent")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAdaptedGradebook {
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String moduleCode;
    @XmlElement(name = "gradeComponentName", required = true, nillable = true)
    private String gradeComponentName;
    @XmlElement(name = "gradeComponentMaxMarks", required = true, nillable = true)
    private int gradeComponentMaxMarks;
    @XmlElement(name = "gradeComponentWeightage", required = true, nillable = true)
    private int gradeComponentWeightage;

    public XmlAdaptedGradebook() {

    }

    public XmlAdaptedGradebook(String moduleCode, String gradeComponentName, int gradeComponentMaxMarks,
                               int gradeComponentWeightage) {
        this.moduleCode = moduleCode;
        this.gradeComponentName = gradeComponentName;
        this.gradeComponentMaxMarks = gradeComponentMaxMarks;
        this.gradeComponentWeightage = gradeComponentWeightage;
    }

    public XmlAdaptedGradebook(String moduleCode, String gradeComponentName) {
        this.moduleCode = moduleCode;
        this.gradeComponentName = gradeComponentName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getGradeComponentName() {
        return gradeComponentName;
    }

    public void setGradeComponentName(String gradeComponentName) {
        this.gradeComponentName = gradeComponentName;
    }

    public int getGradeComponentMaxMarks() {
        return gradeComponentMaxMarks;
    }

    public void setGradeComponentMaxMarks(int gradeComponentMaxMarks) {
        this.gradeComponentMaxMarks = gradeComponentMaxMarks;
    }

    public int getGradeComponentWeightage() {
        return gradeComponentWeightage;
    }

    public void setGradeComponentWeightage(int gradeComponentWeightage) {
        this.gradeComponentWeightage = gradeComponentWeightage;
    }
}
