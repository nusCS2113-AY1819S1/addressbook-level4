package seedu.address.model.gradebook;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a component in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
@XmlRootElement(name = "gradebook")

public class GradebookComponent {

    private String gradeItemName;
    private String moduleCode;

    public GradebookComponent() {

    }
    public GradebookComponent(String gradeItemName, String moduleCode) {
        this.gradeItemName = gradeItemName;
        this.moduleCode = moduleCode;
    }
    @XmlElement(name = "gradeItemName")
    public String getGradeItemName() {
        return gradeItemName;
    }

    public void setGradeItemName(String gradeItemName) {
        this.gradeItemName = gradeItemName;
    }

    @XmlElement(name = "moduleCode")
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}
