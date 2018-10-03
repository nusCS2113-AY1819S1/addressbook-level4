package seedu.address.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a module in Trajectory
 */
@XmlRootElement(name = "module")
public class Module {
    @XmlElement(name = "moduleName", required = true, nillable = true)
    private String name;
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String code;

    public Module() {

    }

    public Module(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getModuleName() {
        return this.name;
    }

    public String getModuleCode() {
        return this.code;
    }
}
