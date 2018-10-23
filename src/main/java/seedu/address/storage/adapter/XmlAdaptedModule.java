package seedu.address.storage.adapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;

/**
 * JAXB-friendly adapted version of the Module.
 */
@XmlRootElement(name = "module")
public class XmlAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

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
        this.name = module.getModuleName().moduleName;
        this.code = module.getModuleCode().moduleCode;
    }

    /**
     * Converts this XmlAdaptedModule into the model's Module object
     */
    public Module toModelType() throws IllegalValueException {
        if (code == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(code)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINT);
        }
        final ModuleCode moduleCode = new ModuleCode(code);

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleName.class.getSimpleName()));
        }
        if (!ModuleName.isValidModuleName(name)) {
            throw new IllegalValueException(ModuleName.MESSAGE_MODULE_NAME_CONSTRAINTS);
        }
        final ModuleName moduleName = new ModuleName(name);

        return new Module(moduleCode, moduleName);
    }
}
