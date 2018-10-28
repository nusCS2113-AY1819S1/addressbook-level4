package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB-friendly representation of the association between Student and Module.
 */
@XmlRootElement(name = "studentModule")
public class XmlAdaptedStudentModule {

    @XmlElement(name = "studentMatricNo", required = true, nillable = true)
    private String studentMatricNo;
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String moduleCode;

    /**
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedStudentModule() { }

    /**
     * Constructs an {@code XmlAdaptedStudentModule} with the given association details.
     */
    public XmlAdaptedStudentModule(String studentMatricNo, String moduleCode) {
        this.studentMatricNo = studentMatricNo;
        this.moduleCode = moduleCode;
    }

    public String getStudentMatricNo() {
        return studentMatricNo;
    }

    public String getModuleCode() {
        return moduleCode;
    }
}
