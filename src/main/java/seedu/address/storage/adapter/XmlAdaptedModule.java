package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.module.Module;

/**
 * JAXB-friendly adapted version of the Module.
 */
@XmlRootElement(name = "module")
public class XmlAdaptedModule {
    @XmlElement(name = "moduleName", required = true, nillable = true)
    private String name;
    @XmlElement(name = "moduleCode", required = true, nillable = true)
    private String code;

    /**
     * Constructs an XmlAdaptedModule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedModule() { }

    /**
     * Constructs an {@code XmlAdaptedModule} with the given module details
     */
    public XmlAdaptedModule(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * Converts a Module into an {@code XmlAdaptedModule} for JAXB use
     */
    public XmlAdaptedModule(Module module) {
        this.name = module.getModuleName();
        this.code = module.getModuleCode();
    }

    /**
     * Converts this XmlAdaptedModule into the model's Module object
     */
    public Module toModelType() {
        return new Module(name, code);
    }

    public String getModuleName() {
        return this.name;
    }

    public String getModuleCode() {
        return this.code;
    }
}
